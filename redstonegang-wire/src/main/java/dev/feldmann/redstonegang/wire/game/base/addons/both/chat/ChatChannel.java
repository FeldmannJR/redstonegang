package dev.feldmann.redstonegang.wire.game.base.addons.both.chat;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.BooleanConfig;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.Server;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public abstract class ChatChannel {

    String channelPrefix;
    String channelDisplayName;
    String channelName;
    Cooldown cd = null;
    protected Server server;

    BooleanConfig ENABLE_CONFIG = null;


    public ChatChannel(char chatPrefix, String displayName, String channelName, Cooldown cooldown) {
        this(String.valueOf(chatPrefix), displayName, channelName, cooldown);
    }

    public ChatChannel(String chatPrefix, String displayName, String channelName, Cooldown cooldown) {
        this.channelPrefix = chatPrefix;
        this.channelDisplayName = displayName;
        this.channelName = channelName;
        this.cd = cooldown;
    }

    public void onEnable(ChatAddon addon) {

    }

    void setServer(Server server) {
        this.server = server;
    }

    public abstract ChatColor getChannelColor();

    public abstract ChatColor getPlayerColor();

    public abstract ChatColor getMessageColor();

    public boolean usePlayerPrefix() {
        return true;
    }

    public boolean canUse(Player p) {
        return true;
    }

    public Cooldown getCooldown() {
        return cd;
    }

    public String getChannelDisplayName() {
        return channelDisplayName;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getChannelPrefix() {
        return channelPrefix;
    }

    public boolean isJoinable() {
        return true;
    }

    public boolean manual() {
        return false;
    }

    public boolean isCommand() {
        return false;
    }

    public boolean canConsoleUse() {
        return false;
    }

    public abstract String handleMessage(Player p, String raw, TextComponent tx);

    public BooleanConfig getEnableConfig() {
        if (ENABLE_CONFIG == null) {
            ENABLE_CONFIG = new BooleanConfig("ad_chat_" + server.getIdentifier() + "_" + channelName.toLowerCase(), true);
        }
        return ENABLE_CONFIG;
    }

    public void handleNetworkMessage(BaseComponent[] component, String... custom) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            User rg = RedstoneGangSpigot.getPlayer(p);
            if (rg.getConfig(getEnableConfig())) {
                p.spigot().sendMessage(component);
            }
        }
        Bukkit.getConsoleSender().sendMessage(new TextComponent(component).toLegacyText());
    }

    public void broadcastMessage(TextComponent component, String... custom) {
        String[] msg = {"chat_" + server.getIdentifier(), getChannelDisplayName(), ComponentSerializer.toString(component)};
        if (custom != null || custom.length > 0) {
            String[] custommsg = Arrays.copyOf(msg, msg.length + custom.length);
            for (int x = 0; x < custom.length; x++) {
                custommsg[msg.length + x] = custom[x];
            }
            msg = custommsg;
        }
        RedstoneGang.instance.network().sendMessageLocal(msg);
    }

    public String getCustomPrefix(Player p) {
        return null;
    }

    public String getCustomSuffix(Player p) {
        return null;
    }


}
