package dev.feldmann.redstonegang.wire.game.base.addons.both.tell;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatChannel;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class TellChannel extends ChatChannel {
    TellAddon addon;

    public TellChannel(TellAddon addon) {
        super("msg", "msg", "privado", null);
        this.addon = addon;
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
    public boolean isJoinable() {
        return false;
    }

    @Override
    public ChatColor getMessageColor() {
        return ChatColor.GRAY;
    }


    @Override
    public String handleMessage(Player p, String raw, TextComponent tx) {
        User user = addon.getUser(p);
        Integer chatting = user.getConfig(addon.CHATTING);
        if (chatting == null || chatting == -1) {
            addon.resetChannel(p);
            return null;
        }
        User target = addon.getUser(chatting);
        if (!addon.checkAvailable(user, target)) {
            addon.resetChannel(p);
            user.setConfig(addon.CHATTING, -1);
            return null;
        }
        addon.sendTell(p, target, raw);
        return null;
    }
}
