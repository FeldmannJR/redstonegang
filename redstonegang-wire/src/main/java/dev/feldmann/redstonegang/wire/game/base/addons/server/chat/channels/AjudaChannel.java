package dev.feldmann.redstonegang.wire.game.base.addons.server.chat.channels;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class AjudaChannel extends ChatChannel {


    public AjudaChannel() {
        super('?', "A", "Ajuda", new Cooldown(30000));
    }

    @Override
    public ChatColor getChannelColor() {
        return ChatColor.DARK_PURPLE;
    }

    @Override
    public ChatColor getPlayerColor() {
        return ChatColor.WHITE;
    }

    @Override
    public ChatColor getMessageColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public String handleMessage(Player p, String raw, TextComponent tx) {
        broadcastMessage(tx);
        return "all";
    }

    @Override
    public boolean isJoinable() {
        return false;
    }

    @Override
    public boolean isCommand() {
        return true;
    }
}
