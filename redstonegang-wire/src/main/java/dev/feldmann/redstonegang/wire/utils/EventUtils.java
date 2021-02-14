package dev.feldmann.redstonegang.wire.utils;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EventUtils {

    public static Player getDamager(EntityDamageByEntityEvent ev) {

        Player d = null;
        if (ev.getDamager() instanceof Player) {
            d = (Player) ev.getDamager();
        }

        if (ev.getDamager() instanceof Projectile) {
            Projectile proj = (Projectile) ev.getDamager();
            if (proj.getShooter() != null && proj.getShooter() instanceof Player) {
                d = (Player) proj.getShooter();
            }
        }
        return d;
    }
}
