package dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandFlags;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperty;

import dev.feldmann.redstonegang.wire.utils.MetaUtils;
import dev.feldmann.redstonegang.wire.utils.items.PotionUtils;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.projectiles.ProjectileSource;

import java.util.Collection;

public class PvPListener extends TerrenoListener {


    public PvPListener(LandAddon manager) {
        super(manager);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void DamagePvPTerreno(EntityDamageByEntityEvent ev) {
        if (ev.isCancelled()) {
            return;
        }

        // Se for um manolo batendo no cara
        Player damager = null;
        if (ev.getEntity() instanceof Player) {
            if (ev.getDamager() instanceof Player) {
                damager = (Player) ev.getDamager();
            }
            // se for um manolo tacando-lhe flecha
            if (ev.getDamager() instanceof Projectile) {
                Projectile proj = (Projectile) ev.getDamager();
                if (proj.getShooter() != null && proj.getShooter() instanceof Player) {
                    damager = (Player) proj.getShooter();
                }
            }
            if (damager != null) {
                Player damaged = (Player) ev.getEntity();
                if (shouldCancelPVP(damager, damaged)) {
                    ev.setCancelled(true);
                    sendDeny(damager, "brigar");
                }

            }
        }
    }

    public boolean shouldCancelPVP(Player damager, Player damaged) {
        if (damaged == damager) return false;
        Land td = getTerreno(damaged);
        Land tr = getTerreno(damager);
        return (td != null && !td.getFlags().get(LandFlags.PVP)) || (tr != null && !tr.getFlags().get(LandFlags.PVP));
    }

    public boolean isPotionGood(ThrownPotion p) {
        Collection<PotionEffect> effects = p.getEffects();
        for (PotionEffect ef : effects) {
            if (PotionUtils.isNegative(ef.getType())) {
                return false;
            }
        }
        return true;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void splash(PotionSplashEvent ev) {
        if (isPotionGood(ev.getPotion())) {
            return;
        }
        ProjectileSource shooter = ev.getPotion().getShooter();
        if (shooter instanceof Player) {
            Player damager = (Player) shooter;

            for (LivingEntity af : ev.getAffectedEntities()) {
                if (af instanceof Player) {
                    if (shouldCancelPVP(damager, (Player) af)) {
                        ev.getAffectedEntities().remove(af);
                    }
                }
                if (af instanceof Animals) {
                    Land t = getTerreno(af);
                    if (t != null) {
                        ev.getAffectedEntities().remove(af);
                    }
                }
            }
        }
    }

    /*
     * Protege os animaizinhos
     * */
    @EventHandler
    public void damage(EntityDamageByEntityEvent ev) {
        if (ev.getEntity() instanceof Animals) {
            Player damager = null;
            if (ev.getDamager() instanceof Player) {
                damager = (Player) ev.getDamager();
            }
            if (ev.getDamager() instanceof Projectile) {
                if (((Projectile) ev.getDamager()).getShooter() != null) {
                    if (((Projectile) ev.getDamager()).getShooter() instanceof Player) {
                        damager = (Player) ((Projectile) ev.getDamager()).getShooter();
                    }
                }
            }
            if (damager == null) {
                return;
            }
            Location l = ev.getEntity().getLocation();
            if (ev.getEntity() instanceof Wolf) {
                if (((Wolf) ev.getEntity()).isAngry() || ((Wolf) ev.getEntity()).getTarget() == damager) {
                    return;
                }
            }
            if (byPass(damager)) {
                return;
            }
            if (can(damager, l, PlayerProperty.BUILD, true)) {
                return;
            }
            sendDeny(damager, "matar animais");
            ev.setCancelled(true);
        }

    }

    @EventHandler
    public void mobspawner(EntityDamageByEntityEvent ev) {
        Player damager = null;
        if (ev.getDamager() instanceof Player) {
            damager = (Player) ev.getDamager();
        }
        if (ev.getDamager() instanceof Projectile) {
            if (((Projectile) ev.getDamager()).getShooter() != null) {
                if (((Projectile) ev.getDamager()).getShooter() instanceof Player) {
                    damager = (Player) ((Projectile) ev.getDamager()).getShooter();
                }
            }
        }
        if (damager == null) {
            return;
        }
        if (ev.getDamager() instanceof LivingEntity && !(ev.getEntity() instanceof Player)) {
            CreatureSpawnEvent.SpawnReason r = (CreatureSpawnEvent.SpawnReason) MetaUtils.get(ev.getEntity(), "spawncause");
            if (r != null) {
                if (r == CreatureSpawnEvent.SpawnReason.SPAWNER) {
                    if (!can(damager, ev.getEntity(), PlayerProperty.MOBSPAWNERS, true)) {
                        ev.setCancelled(true);
                        sendDeny(damager, "usar mobspawners");
                    }
                }
            }
        }


    }
}
