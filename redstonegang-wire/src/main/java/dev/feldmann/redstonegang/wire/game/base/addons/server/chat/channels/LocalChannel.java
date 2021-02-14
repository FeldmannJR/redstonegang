package dev.feldmann.redstonegang.wire.game.base.addons.server.chat.channels;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static dev.feldmann.redstonegang.wire.RedstoneGangSpigot.getPlayer;

public class LocalChannel extends ChatChannel {

    private static int localRange = 16 * 5;

    Server sv;

    public LocalChannel(Server sv) {
        super(':', "l", "Local", new Cooldown(500));
        this.sv = sv;
    }

    @Override
    public ChatColor getChannelColor() {
        return ChatColor.YELLOW;
    }

    @Override
    public ChatColor getPlayerColor() {
        return ChatColor.WHITE;
    }

    @Override
    public ChatColor getMessageColor() {
        return ChatColor.YELLOW;
    }

    @Override
    public String getCustomPrefix(Player p) {
        if (sv.hasAddon(ClanAddon.class)) {

            ClanAddon clan = sv.getAddon(ClanAddon.class);
            ClanMember member = clan.getCache().getMember(p);
            if (member.getClan() != null) {
                return "§f" + member.getClan().getColorTag() + "§7.";
            }
        }


        return null;
    }

    @Override
    public String handleMessage(Player p, String raw, TextComponent tx) {
        List<Player> toSend = new ArrayList<>();
        int nonVanished = 0;
        for (Entity t : p.getNearbyEntities(localRange, 255, localRange)) {
            if (t.hasMetadata("NPC")) {
                continue;
            }
            if (t instanceof Player && getPlayer((Player) t).getConfig(getEnableConfig())) {
                toSend.add((Player) t);
                if (p.canSee((Player) t)) {
                    nonVanished++;
                }

            }
        }
        if (nonVanished == 0) {
            C.error(p, "Não tem ninguém perto para ver a mensagem!");
            return null;
        }

        String receivers = "";
        toSend.add(p);
        for (Player send : toSend) {
            send.spigot().sendMessage(tx);
            receivers += RedstoneGangSpigot.getPlayer(send).getId() + ",";
        }
        Bukkit.getConsoleSender().sendMessage(tx.toLegacyText());
        return receivers;
    }

}
