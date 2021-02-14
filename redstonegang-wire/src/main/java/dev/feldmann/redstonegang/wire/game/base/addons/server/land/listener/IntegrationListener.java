package dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener;

import dev.feldmann.redstonegang.wire.game.base.addons.server.home.events.PlayerSetHomeEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperty;
import dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator.events.PlayerUseElevatorEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;

public class IntegrationListener extends TerrenoListener {

    public IntegrationListener(LandAddon manager) {
        super(manager);
    }

    //Usar elevadores
    @EventHandler
    public void elevator(PlayerUseElevatorEvent ev) {
        if (byPass(ev.getPlayer())) {
            return;
        }
        if (!can(ev.getPlayer(), ev.getBlock(), PlayerProperty.ELEVATOR, true)) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void setHome(PlayerSetHomeEvent ev) {
        if (byPass(ev.getPlayer())) return;
        Location loc = ev.getLocation().toLocation();
        if (!can(ev.getPlayer(), loc, PlayerProperty.BUILD, false)) {
            sendDeny(ev.getPlayer(), "setar home");
            ev.setCancelled(true);
        }
    }

}
