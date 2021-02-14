package dev.feldmann.redstonegang.wire.modulos.customevents;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CustomEvents extends Modulo implements Listener {
    @Override
    public void onEnable() {
        register(this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void spawn(CreatureSpawnEvent ev) {
        if (ev.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING) {
            for (Entity e : ev.getEntity().getNearbyEntities(5, 5, 5)) {
                if (e instanceof Player) {
                    Wire.callEvent(new PlayerBreedingEntityEvent((Player) e, ev.getEntity()));
                }
            }

        }
    }
}
