package dev.feldmann.redstonegang.wire.game.base.addons.both.tell;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.config.types.EnumConfig;
import dev.feldmann.redstonegang.common.player.config.types.IntegerConfig;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.tell.cmds.Msg;
import dev.feldmann.redstonegang.wire.game.base.addons.both.tell.cmds.Responder;
import dev.feldmann.redstonegang.wire.game.base.addons.server.chat.ServerChatAddon;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

@Dependencies(soft = ServerChatAddon.class)
public class TellAddon extends Addon {

    public EnumConfig<TellAvailability> TELL_AVAILABILITY;
    public IntegerConfig LAST_RECEIVED;
    public IntegerConfig CHATTING;
    private TellChannel channel;
    private MsgType tellChat = new MsgType("§d[Privado] ", ChatColor.GRAY, ChatColor.LIGHT_PURPLE, ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE, ChatColor.WHITE);


    @Override
    public void onEnable() {
        TELL_AVAILABILITY = new EnumConfig<TellAvailability>(generateConfigName("availability"), TellAvailability.Ligado);
        LAST_RECEIVED = new IntegerConfig(generateConfigName("last"), -1);
        CHATTING = new IntegerConfig(generateConfigName("chatting"), -1);

        addConfig(
                TELL_AVAILABILITY,
                LAST_RECEIVED,
                CHATTING
        );
        channel = new TellChannel(this);
        aOf(ChatAddon.class).registerChannel(channel);
        registerCommand(new Responder(this), new Msg(this));
    }


    public void sendTell(Player player, User receiver, String msg) {
        User sender = getUser(player);
        if (!checkAvailable(sender, receiver)) {
            return;
        }
        aOf(ChatAddon.class).getHistory().insert(sender.getId(), channel.getChannelDisplayName(), msg, receiver.getId() + "", RedstoneGang.instance().getNomeServer());
        Player onlineReceiver = RedstoneGangSpigot.getOnlinePlayer(receiver);
        // Se tiver online nem manda socket
        if (onlineReceiver != null && onlineReceiver.isOnline()) {
            handleTell(sender.getId(), receiver.getId(), msg);
        } else {
            network().sendMessage("tell", sender.getId(), receiver.getId(), msg);
        }
        C.send(player, tellChat, "para %%: " + msg, receiver);
    }

    public void joinChannel(Player player, User target) {
        if (!checkAvailable(getUser(player), target)) {
            return;
        }
        aOf(ChatAddon.class).setChannel(getUser(player), channel);
        User user = getUser(player);
        user.setConfig(CHATTING, target.getId());
        C.info(player, "Agora você está falando com %% !", target);
    }

    public void resetChannel(Player player) {
        aOf(ChatAddon.class).changeToChannel(player, aOf(ChatAddon.class).getDefautlChannel());
    }


    public boolean checkAvailable(User sender, User receiver) {
        if (!receiver.isOnline() || receiver.getConfig(this.TELL_AVAILABILITY) == TellAvailability.Desligado) {
            C.error(getOnlinePlayer(sender.getId()), "Jogador está offline ou com o privado desligado!");
            return false;
        }
        return true;
    }

    public User getLastReceived(User user) {
        Integer last = user.getConfig(LAST_RECEIVED);
        if (last == null || last == -1) return null;
        return RedstoneGang.getPlayer(last);
    }

    @EventHandler
    public void handle(NetworkMessageEvent ev) {
        if (ev.is("tell")) {
            int sender = Integer.parseInt(ev.get(0));
            int receiver = Integer.parseInt(ev.get(1));
            String msg = ev.get(2);
            handleTell(sender, receiver, msg);
        }
    }

    private void handleTell(int sender, int receiver, String msg) {
        User user = RedstoneGang.instance().user().getCachedUser(receiver);
        if (user != null) {
            Player online = RedstoneGangSpigot.getOnlinePlayer(receiver);
            if (online != null && online.isOnline()) {
                sendTellmessage(online, RedstoneGang.getPlayer(sender), msg);
                user.setConfig(this.LAST_RECEIVED, sender);
            }
        }
    }


    private void sendTellmessage(Player target, User sender, String message) {
        C.send(target, tellChat, "de %%: " + message, sender);
    }

    public enum TellAvailability {
        Ligado,
        Desligado
    }
}
