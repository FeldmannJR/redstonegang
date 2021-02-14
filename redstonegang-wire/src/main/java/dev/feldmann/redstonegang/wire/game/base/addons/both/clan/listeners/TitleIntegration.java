package dev.feldmann.redstonegang.wire.game.base.addons.both.clan.listeners;

import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.both.clan.ClanAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.titulos.events.PlayerGetTitlesEvent;
import org.bukkit.event.EventHandler;

public class TitleIntegration extends BaseListener {

    ClanAddon addon;

    public TitleIntegration(ClanAddon addon) {
        this.addon = addon;
    }

    @EventHandler
    public void getTitle(PlayerGetTitlesEvent ev) {
        if (addon.getCache().hasClan(ev.getOwner()))
            ev.addTitle(new ClanTitle(addon, ev.getOwner()));
    }
}
