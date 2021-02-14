package dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar;

import dev.feldmann.redstonegang.common.utils.RandomUtils;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MinerarMobsAddon extends Addon {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void spawn(CreatureSpawnEvent ev) {
        if (ev.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            if (ev.getEntityType() == EntityType.SKELETON ||
                    ev.getEntityType() == EntityType.ZOMBIE ||
                    ev.getEntityType() == EntityType.CREEPER ||
                    ev.getEntityType() == EntityType.SPIDER) {
                int random = RandomUtils.randomInt(0, 30);
                EntityType type = null;
                if (random <= 1) {
                    type = EntityType.PIG_ZOMBIE;
                } else if (random <= 3) {
                    type = EntityType.MAGMA_CUBE;
                } else if (random == 4) {
                    type = EntityType.BLAZE;
                } else if (RandomUtils.oneIn(50)) {
                    type = EntityType.GHAST;
                } else if (RandomUtils.oneIn(50)) {
                    type = EntityType.GIANT;
                }
                if (type != null) {
                    Location loc = ev.getEntity().getLocation();
                    if (type == EntityType.GHAST) {
                        int y = loc.getWorld().getHighestBlockYAt(loc);
                        if (Math.abs(loc.getY() - y) > 10) {
                            return;
                        }
                    }
                    Entity entity = ev.getEntity().getWorld().spawnEntity(loc, type);
                    if (entity instanceof PigZombie) {
                        ((PigZombie) entity).setAnger(Integer.MAX_VALUE);
                    }
                    ev.getEntity().remove();
                    return;
                }
            }
            if (ev.getEntityType() == EntityType.CREEPER) {
                if (RandomUtils.oneIn(10)) {
                    ((Creeper) ev.getEntity()).setPowered(true);
                }
            }
        }
    }
}
