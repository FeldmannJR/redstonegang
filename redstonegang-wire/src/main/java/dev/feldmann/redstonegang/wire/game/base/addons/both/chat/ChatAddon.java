package dev.feldmann.redstonegang.wire.game.base.addons.both.chat;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.network.NetworkMessage;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.StringConfig;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.punishment.Punishment;
import dev.feldmann.redstonegang.common.utils.formaters.DateUtils;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.wire.utils.msgs.C;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public abstract class ChatAddon extends Addon {


    StringConfig PLAYER_CHANNEL_CONFIG;

    private HashMap<String, ChatChannel> channels = new HashMap<>();

    private static String translateColor = "rg.chat.translateColor";

    private String db;
    private ChatHistoryDB history;

    public ChatAddon(String db) {
        this.db = db;
    }


    @Override
    public void onEnable() {
        PLAYER_CHANNEL_CONFIG = new StringConfig("ad_chat_" + getServer().getIdentifier() + "_current", "null");
        addOption(new PermissionDescription("Cores no chat", translateColor, "quando o jogador falar no chat pode usar cores"));
        this.history = new ChatHistoryDB(db);
        addConfig(PLAYER_CHANNEL_CONFIG);
    }

    @Override
    public void onStart() {

    }

    public void registerChannel(ChatChannel channel) {
        channel.server = getServer();
        channels.put(channel.getChannelPrefix(), channel);
        addConfig(channel.getEnableConfig());
        channel.onEnable(this);
        if (channel.isCommand()) {
            registerCommand(new ChannelCommand(channel, this));
        }
    }

    public ChatHistoryDB getHistory() {
        return history;
    }

    public TextComponent formatMessage(Player p, String msg, String channel, ChatColor channelColor, ChatColor msgColor, ChatColor playerColor, boolean useTag, String customPrefix, String customSuffix) {
        TextComponent pl = C.processPlayer(p, useTag, customPrefix, customSuffix, playerColor);
        TextComponent txchannel = new TextComponent("[" + channel + "]");
        txchannel.setColor(channelColor);
        txchannel.addExtra(" ");
        txchannel.addExtra(pl);
        txchannel.addExtra(new TextComponent((new ComponentBuilder(": ").color(channelColor).create())));
        String smsg = msg;
        if (p.hasPermission(translateColor)) {
            smsg = ChatColor.translateAlternateColorCodes('&', msg);
            smsg.replace("§k", "");
            smsg.replace("§l", "");
            if (ChatColor.stripColor(smsg).isEmpty()) {
                return null;
            }
        }
        TextComponent msgtxt = new TextComponent(TextComponent.fromLegacyText(msgColor + smsg));
        msgtxt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Hora: " + DateUtils.currentTime()).color(ChatColor.GRAY).create()));
        txchannel.addExtra(msgtxt);
        return txchannel;
    }


    public void handleChannel(ChatChannel channel, Player p, String msg) {
        if(RedstoneGang.instance().user().getPunishment().isMuted(RedstoneGang.getPlayer(p.getUniqueId())))
        {
            Punishment mute = RedstoneGang.instance().user().getPunishment().hasMuted(RedstoneGang.getPlayer(p.getUniqueId()));
            C.error(p, "Você está mutado, aguarde %% minutos para falar novamente!", mute.getTimeLeft().getMinutes());
            return;
        }
        if (!channel.canUse(p)) {
            C.error(p, "Você não pode usar este canal de texto!");
            return;
        }
        TextComponent txt = formatMessage(p, msg, channel.getChannelDisplayName(), channel.getChannelColor(), channel.getMessageColor(), channel.getPlayerColor(), channel.usePlayerPrefix(), channel.getCustomPrefix(p), channel.getCustomSuffix(p));
        if (txt == null) {
            C.error(p, "Você não pode mandar uma mensagem vazia!");
            return;
        }
        if (!getUser(p).getConfig(channel.getEnableConfig())) {
            C.error(p, "Você está com o chat " + channel.getChannelName() + " desligado!");
            return;
        }
        User rg = getUser(p);
        if (channel.getCooldown() != null && !rg.isStaff()) {
            if (channel.getCooldown().isCooldownWithMessage(p.getUniqueId())) {
                return;
            }
        }
        String handle = channel.handleMessage(p, msg, txt);
        if (handle != null) {
            history.insert(rg.getId(), channel.getChannelDisplayName(), msg, handle, getServer().getIdentifier());
        }
    }

    public void changeToChannel(Player player, ChatChannel channel) {
        if (channel == getDefaultChannel(player)) {
            C.error(player, "Você já está falando neste canal!");
        } else {
            if (channel == getDefautlChannel()) {
                C.info(player, "Agora você está falando no chat %% !", channel.getChannelColor() + channel.getChannelName());
            } else {
                C.info(player, "Agora você está falando no chat %% para voltar para o chat padrão use %% !", channel.getChannelColor() + channel.getChannelName(), "" + getDefautlChannel().getChannelPrefix());
            }
            setChannel(getUser(player), channel);
        }
    }

    public void setChannel(User user, ChatChannel channel) {
        user.setConfig(PLAYER_CHANNEL_CONFIG, channel == null ? "null" : channel.channelDisplayName);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void AsyncChatEvent(AsyncPlayerChatEvent ev) {
        if (ev.getMessage().isEmpty()) {
            return;
        }
        ChatChannel channel = null;
        for (ChatChannel value : channels.values()) {
            String prefix = value.getChannelPrefix();
            if (ev.getMessage().startsWith(prefix)) {
                channel = value;
            }
        }
        if (channel != null && !channel.isCommand()) {
            if (ev.getMessage().length() == channel.getChannelPrefix().length() && channel.canUse(ev.getPlayer()) && channel.isJoinable()) {
                changeToChannel(ev.getPlayer(), channel);
                ev.setCancelled(true);
                return;
            }
            handleChannel(channel, ev.getPlayer(), ev.getMessage().substring(channel.getChannelPrefix().length()).trim());
        } else {
            handleChannel(getDefaultChannel(ev.getPlayer()), ev.getPlayer(), ev.getMessage().trim());
        }
        ev.setCancelled(true);
        ev.getRecipients().clear();
    }

    public abstract ChatChannel getDefautlChannel();

    public ChatChannel getDefaultChannel(Player p) {
        User rg = getUser(p);
        String cur = rg.getConfig(this.PLAYER_CHANNEL_CONFIG);
        if (cur.equals("null")) {
            return getDefautlChannel();
        }
        ChatChannel curChannel = getChannelByName(cur);
        if (curChannel != null) {
            return curChannel;
        }
        return getDefautlChannel();
    }

    public ChatChannel getChannelByName(String letter) {
        for (ChatChannel channel : channels.values()) {
            if (channel.getChannelDisplayName().equals(letter)) {
                return channel;
            }
        }
        return null;
    }


    @EventHandler
    public void handleMessage(NetworkMessageEvent ev) {
        if (ev.getId().equalsIgnoreCase("chat_" + getServer().getIdentifier())) {
            String channel = ev.get(0);
            BaseComponent[] txt = ComponentSerializer.parse(ev.get(1));
            ChatChannel ch = getChannelByName(channel);
            if (ch != null) {
                NetworkMessage msg = ev.getMsg();
                int start = 3;
                String[] custom = new String[msg.getLength() - 3];
                for (int x = start; x < msg.getLength(); x++) {
                    custom[x - start] = msg.get(x);
                }
                ch.handleNetworkMessage(txt, custom);
            }

        }
    }
}
