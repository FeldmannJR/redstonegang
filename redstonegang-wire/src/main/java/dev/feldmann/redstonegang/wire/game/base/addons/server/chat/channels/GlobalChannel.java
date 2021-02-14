package dev.feldmann.redstonegang.wire.game.base.addons.server.chat.channels;

import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatChannel;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.ChatAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.EconomyAddon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class GlobalChannel extends ChatChannel {

    private int globalPrice;
    private static String freeGlobal = "rg.serverchat.freeglobal";


    Server sv;

    public GlobalChannel(int globalPrice, Server sv) {
        super('!', "G", "Global", new Cooldown(15000));
        this.globalPrice = globalPrice;
        this.sv = sv;
    }

    @Override
    public ChatColor getChannelColor() {
        return ChatColor.DARK_GREEN;
    }

    @Override
    public ChatColor getPlayerColor() {
        return ChatColor.WHITE;
    }

    @Override
    public ChatColor getMessageColor() {
        return ChatColor.GREEN;
    }

    @Override
    public void onEnable(ChatAddon addon) {
        addon.addOption(new PermissionDescription("Global free", freeGlobal, "não precisa pagar para usar o chat global"));
    }

    @Override
    public String getCustomPrefix(Player p) {
        if (sv.hasAddon(ClanAddon.class)) {

            ClanAddon clan = sv.getAddon(ClanAddon.class);
            ClanMember member = clan.getCache().getMember(p);
            if (member.getClanTag() != null) {
                return "§f" + member.getClan().getColorTag() + "§7.";
            }
        }
        return null;

    }

    @Override
    public String handleMessage(Player p, String raw, TextComponent tx) {
        if (!p.hasPermission(freeGlobal)) {
            if (!server.a(EconomyAddon.class).hasWithMessage(p, globalPrice)) {
                return null;
            }
            server.a(EconomyAddon.class).remove(p, globalPrice);
        }
        broadcastMessage(tx);
        return "all";
    }
}
