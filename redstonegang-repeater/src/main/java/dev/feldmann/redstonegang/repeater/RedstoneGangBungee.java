package dev.feldmann.redstonegang.repeater;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.ServerType;
import dev.feldmann.redstonegang.common.player.User;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class RedstoneGangBungee extends RedstoneGang {
    @Override
    public ServerType getServerType() {
        return ServerType.BUNGEE;
    }

    @Override
    public void runRepeatingTask(Runnable runnable, int i) {
        Repeater.getInstance().getProxy().getScheduler().schedule(Repeater.getInstance(), runnable, i * 50, i * 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public void runSync(Runnable runnable) {
        Repeater.getInstance().getProxy().getScheduler().runAsync(Repeater.getInstance(), runnable);
    }

    @Override
    public void runAsync(Runnable runnable) {
        Repeater.getInstance().getProxy().getScheduler().runAsync(Repeater.getInstance(), runnable);
    }

    @Override
    public void sendMessage(User rgPlayer, TextComponent... textComponent) {
        ProxiedPlayer pl = Repeater.getInstance().getProxy().getPlayer(rgPlayer.getUuid());
        if (pl != null && pl.isConnected()) {
            for (TextComponent component : textComponent) {
                pl.sendMessage(component);
            }
        } else {
            sendUserMessageSocket(rgPlayer, textComponent);
        }
    }

    public static User getPlayer(ProxiedPlayer pl) {
        return getPlayer(pl.getUniqueId());
    }

    public static ProxiedPlayer getOnlinePlayer(int userId) {
        User id = getPlayer(userId);
        UUID uuid = id.getUuid();
        return Repeater.getInstance().getProxy().getPlayer(uuid);
    }

    @Override
    public Logger getLogger() {
        return Repeater.getInstance().getLogger();
    }

    @Override
    public void shutdown(String s) {
        Repeater.getInstance().getProxy().stop(s);
    }
}
