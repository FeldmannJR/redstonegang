package dev.feldmann.redstonegang.repeater.modules;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.repeater.RedstoneGangBungee;
import dev.feldmann.redstonegang.repeater.Repeater;
import dev.feldmann.redstonegang.repeater.events.NetworkMessageEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class IntegrationModule extends Module {


    // Quando muda de server manda mensagem pra todos mundo pra atualizar o cache
    @EventHandler(priority = EventPriority.HIGHEST)
    public void connected(ServerConnectedEvent ev) {
        ProxiedPlayer p = ev.getPlayer();
        RedstoneGang.instance().user().updateServer(RedstoneGangBungee.getPlayer(p), RedstoneGang.instance().getNomeServer(), ev.getServer().getInfo().getName());
    }

    @EventHandler
    public void join(PostLoginEvent ev) {
        String address = ev.getPlayer().getAddress().getAddress().getHostName();
        RedstoneGangBungee.getPlayer(ev.getPlayer()).setIp(address);
    }

    // Mandar mensagem especifica pra um nego que ta no server
    @EventHandler
    public void sendMessage(NetworkMessageEvent ev) {
        if (ev.is("sendmessage")) {
            Integer userId = ev.getInt(0);
            if (userId != null) {
                // Se não tiver cacheado nem vou buscar no banco, n preciso disso mesmo azar
                boolean cached = RedstoneGang.instance().user().cache.isCached(userId);
                if (cached) {
                    ProxiedPlayer player = RedstoneGangBungee.getOnlinePlayer(userId);
                    if (player != null && player.isConnected()) {
                        for (int x = 1; x < ev.getLength(); x++) {
                            String message = ev.get(x);
                            player.sendMessage(ComponentSerializer.parse(message));
                        }
                    }
                }

            }
        }
    }

    // Mandar mensagem especifica pra um nego que ta no server
    @EventHandler
    public void broadcastMessage(NetworkMessageEvent ev) {
        if (ev.is("broadcastmessage")) {
            BaseComponent[][] messages = new BaseComponent[ev.getLength()][];
            for (int x = 0; x < ev.getLength(); x++) {
                messages[x] = (ComponentSerializer.parse(ev.get(x)));
            }
            for (ProxiedPlayer player : Repeater.getInstance().getProxy().getPlayers()) {
                for (BaseComponent[] message : messages) {
                    player.sendMessage(message);
                }
            }
        }
    }


    // Mandar mensagem especifica pra um nego que ta no server
    @EventHandler
    public void kickPlayer(NetworkMessageEvent ev) {
        if (ev.is("kick")) {
            Integer userId = ev.getInt(0);
            String message = ev.get(1);
            if (userId != null) {
                ProxiedPlayer player = RedstoneGangBungee.getOnlinePlayer(userId);
                if (player != null && player.isConnected()) {
                    if (message != null) {
                        player.disconnect(ComponentSerializer.parse(message));
                    } else {
                        player.disconnect();
                    }
                }
            }
        }
    }


}
