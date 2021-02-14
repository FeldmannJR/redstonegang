package dev.feldmann.redstonegang.wire.game.games.other.mapconfig;

import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class MapGameListener extends BaseListener {


    @EventHandler
    public void damage(EntityDamageEvent ev) {
        if (ev.getEntity() instanceof Player) {
            ev.setCancelled(true);
            if (ev.getCause() == EntityDamageEvent.DamageCause.VOID) {
                ev.getEntity().teleport(ev.getEntity().getWorld().getSpawnLocation());
            }

        }
    }

}
