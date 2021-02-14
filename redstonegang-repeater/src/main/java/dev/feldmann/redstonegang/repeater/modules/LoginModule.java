package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.repeater.RedstoneGangBungee;
import dev.feldmann.redstonegang.repeater.Repeater;
import dev.feldmann.redstonegang.repeater.events.NetworkMessageEvent;
import dev.feldmann.redstonegang.repeater.events.PlayerLoadEvent;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.event.EventHandler;

import java.net.InetAddress;
import java.util.HashMap;

public class LoginModule extends Module implements ReconnectHandler {

    public static HashMap<Integer, InetAddress> loggedIn = new HashMap<>();

    @Override
    public void onEnable() {
        proxy().setReconnectHandler(this);
    }

    @EventHandler
    public void network(NetworkMessageEvent ev) {
        if (ev.is("login")) {
            int playerId = ev.getInt(0);
            ProxiedPlayer online = RedstoneGangBungee.getOnlinePlayer(playerId);
            if (online != null && online.isConnected()) {
                loggedIn.put(playerId, online.getAddress().getAddress());
                loggedIn(online);
            }
        }
    }


    public void loggedIn(ProxiedPlayer player) {
        ServerInfo teleport = Repeater.getInstance().servers().getDefault();
        player.connect(teleport, (connected, error) -> {
            if (!connected) {
                player.disconnect(TextComponent.fromLegacyText("§cOcorreu um erro ao logar-se!"));
            }
        });
    }

    @EventHandler
    public void kick(ServerKickEvent ev) {
        if (ev.getPlayer().getServer().getInfo().getName().toLowerCase().startsWith("login")) {
            ev.getPlayer().disconnect(ev.getKickReasonComponent());
            ev.setCancelled(true);
        }
    }

    public boolean isAuth(ProxiedPlayer player) {
        int id = RedstoneGangBungee.getPlayer(player).getId();
        if (loggedIn.containsKey(id)) {
            if (loggedIn.get(id).equals(player.getAddress().getAddress())) {
                return true;
            }
        }
        return false;
    }


    @EventHandler
    public void connect(ServerConnectEvent ev) {
        ProxiedPlayer p = ev.getPlayer();
        boolean premium = p.getPendingConnection().isOnlineMode();
        ServerInfo defaul = Repeater.getInstance().servers().getDefault();
        if (ev.getTarget().getName().toLowerCase().startsWith("login")) {
            if (premium || isAuth(p)) {
                ev.setTarget(defaul);
            }
        } else {
            if (!premium && !isAuth(p)) {
                ServerInfo login = Repeater.getInstance().servers().getLogin();
                ev.setTarget(login);
            }
        }
    }


    public void connect(ProxiedPlayer p, ServerInfo info) {
        if (p.getServer() != null && p.getServer().getInfo() != null && p.getServer().getInfo().equals(info)) {
            return;
        }
        p.connect(info);
    }


    @EventHandler
    public void postLogin(PlayerLoadEvent ev) {
        loggedIn.remove(ev.getPlayer().getId());
    }

    /**
     * O bungee chama esse metodo pra escolher qual server mandar o nego que logou
     * é chamado após o post login então em teoria é pra ter acesso ao jogador
     * Então a ordem de login fica
     * PreLoginEvent -> (LoginEvent -> PlayerLoadEvent(Executado Async)) -> getServer ->  PostLoginEvent
     * https://github.com/SpigotMC/BungeeCord/blob/master/proxy/src/main/java/net/md_5/bungee/connection/InitialHandler.java#L526
     */
    @Override
    public ServerInfo getServer(ProxiedPlayer proxiedPlayer) {
        if (proxiedPlayer.getPendingConnection().isOnlineMode()) {
            return Repeater.getInstance().servers().getDefault();
        }
        return Repeater.getInstance().servers().getLogin();
    }

    @Override
    public void setServer(ProxiedPlayer proxiedPlayer) {

    }

    @Override
    public void save() {

    }

    @Override
    public void close() {

    }
}
