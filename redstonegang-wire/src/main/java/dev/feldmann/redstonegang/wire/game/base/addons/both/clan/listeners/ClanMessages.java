package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.listeners;

import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.Clan;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanMember;
import dev.feldmann.redstonegang.wire.plugin.events.NetworkMessageEvent;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class ClanMessages extends BaseListener {
    ClanAddon addon;

    public ClanMessages(ClanAddon addon) {
        this.addon = addon;
    }

    public void sendClanMessage(Clan c, TextComponent tc) {
        String msg = ComponentSerializer.toString(tc);
        addon.networkLocal("msg", c.getTag(), msg);
    }


    @EventHandler
    public void messageRece(NetworkMessageEvent ev) {
        if (ev.getId().equals("clan") && ev.get(0).equals(addon.getIdentifier())) {
            if (ev.get(1).equalsIgnoreCase("msg")) {
                String ctag = ev.get(2);
                String msg = ev.get(3);
                sendMessageLocally(ctag, msg);
            }
        }
    }

    private void sendMessageLocally(String ctag, String message) {
        BaseComponent[] msg = ComponentSerializer.parse(message);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.isOnline()) {
                ClanMember cm = addon.getCache().getMember(p);
                if (cm.getClan() != null && cm.getClanTag().equals(ctag)) {
                    p.spigot().sendMessage(msg);
                }
            }
        }
    }
}
