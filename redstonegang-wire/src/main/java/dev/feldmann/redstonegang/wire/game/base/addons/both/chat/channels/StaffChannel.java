package dev.feldmann.redstonegang.wire.game.base.addons.both.chat.channels;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StaffChannel extends ChatChannel {


    public StaffChannel() {
        super('@', "STAFF", "Staff", null);
    }

    @Override
    public ChatColor getChannelColor() {
        return ChatColor.AQUA;
    }

    @Override
    public ChatColor getPlayerColor() {
        return ChatColor.WHITE;
    }

    @Override
    public ChatColor getMessageColor() {
        return ChatColor.AQUA;
    }

    @Override
    public boolean canUse(Player p) {
        User pl = RedstoneGangSpigot.getPlayer(p);
        return pl.isStaff();
    }

    @Override
    public String handleMessage(Player p, String raw, TextComponent tx) {
        broadcastMessage(tx);
        return "staff";
    }

    @Override
    public void handleNetworkMessage(BaseComponent[] component, String... custom) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RedstoneGangSpigot.getPlayer(p).isStaff()) {
                p.spigot().sendMessage(component);
            }
        }
        Bukkit.getConsoleSender().sendMessage(new TextComponent(component).toLegacyText());
    }
}
