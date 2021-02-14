package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.integration;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanRole;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClanChatChannel extends ChatChannel {
    ClanAddon addon;


    public ClanChatChannel(ClanAddon addon) {
        super('.', "c", "Clan", new Cooldown(500));
        this.addon = addon;
    }

    @Override
    public ChatColor getChannelColor() {
        return ChatColor.RED;
    }

    @Override
    public ChatColor getPlayerColor() {
        return ChatColor.WHITE;
    }

    @Override
    public ChatColor getMessageColor() {
        return ChatColor.RED;
    }

    @Override
    public boolean canUse(Player p) {
        ClanMember member = addon.getCache().getMember(p);
        return member.getClanTag() != null;
    }

    @Override
    public boolean usePlayerPrefix() {
        return false;
    }

    @Override
    public String getCustomPrefix(Player p) {
        ClanMember member = addon.getCache().getMember(p);
        if (member != null) {
            switch (member.getRole()) {
                case ClanRole.SUBLEADER:
                    return "§2Sub-Líder ";
                case ClanRole.LEADER:
                    return "§4Líder ";
                case ClanRole.STAFF:
                    return "§6§lSTAFF ";
                default:
                    return null;
            }

        }
        return null;
    }

    @Override
    public String handleMessage(Player p, String raw, TextComponent tx) {
        ClanMember member = addon.getCache().getMember(p);
        broadcastMessage(tx, member.getClanTag());
        return member.getClanTag();

    }

    @Override
    public void handleNetworkMessage(BaseComponent[] component, String... custom) {
        String clantag = custom[0];
        String tx = "§7" + clantag + " §8" + new TextComponent(component).toPlainText().substring(4);
        for (Player p : Bukkit.getOnlinePlayers()) {
            User rg = addon.getUser(p);
            String tag = addon.getCache().getMember(p).getClanTag();
            if ((tag != null && tag.equals(clantag))) {
                if (rg.getConfig(getEnableConfig())) {
                    p.spigot().sendMessage(component);
                }
            }
            if (p.hasPermission(ClanAddon.VIEW_CLAN_MESSAGES)) {
                if (rg.getConfig(addon.STAFF_CHAT_CONFIG)) {
                    p.sendMessage("§8" + tx);
                }
            }

        }

    }
}
