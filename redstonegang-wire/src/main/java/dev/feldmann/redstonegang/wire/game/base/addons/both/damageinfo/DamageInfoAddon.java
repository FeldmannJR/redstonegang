package dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo;


import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import net.minecraft.server.v1_8_R3.DamageSource;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

/**
 * @author Carlos
 */
public class DamageInfoAddon extends Addon {


    static long DAMAGE_DECAY = 7000;

    private static HashMap<UUID, List<HitInfo>> infos = new HashMap();
    private static HashMap<UUID, List<DeathInfo>> mortes = new HashMap();
    private Cooldown voidDamageCD = new Cooldown(500);


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void damage(EntityDamageEvent ev) {
        if (ev.getEntity() instanceof Player) {
            Player tomou = (Player) ev.getEntity();
            HitType type = HitType.PHYSICAL;
            Entity machucou = null;
            ItemStack item = null;

            EntityDamageEvent.DamageCause causa = ev.getCause();
            if (causa == EntityDamageEvent.DamageCause.VOID) {
                ev.setDamage(999999);
            }
            if (ev instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent eev = (EntityDamageByEntityEvent) ev;
                machucou = eev.getDamager();
                if (ev.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK && machucou != tomou) {
                    if (machucou instanceof LivingEntity) {
                        LivingEntity machucoul = (LivingEntity) machucou;
                        if (machucoul.getEquipment() != null) {
                            if (machucoul.getEquipment().getItemInHand() != null && machucoul.getEquipment().getItemInHand().getType() != Material.AIR) {
                                ItemStack i = machucoul.getEquipment().getItemInHand();
                                item = i;
                            }
                        }
                    }
                }

                if (eev.getDamager() instanceof Projectile) {
                    Projectile proj = (Projectile) eev.getDamager();
                    if (proj.getShooter() != null && proj.getShooter() instanceof Entity) {
                        machucou = (Entity) proj.getShooter();
                        if (proj instanceof Arrow) {
                            type = HitType.ARROW;
                        }
                        if (proj instanceof Snowball) {
                            type = HitType.SNOW_BALL;
                        }
                        if (proj instanceof Egg) {
                            type = HitType.EGG;
                        }
                        if (proj.hasMetadata("origem")) {
                            ItemStack arco = (ItemStack) proj.getMetadata("origem").get(0).value();
                            if (arco != null) {
                                item = arco;
                            }
                        }
                    } else {
                        type = HitType.PROJECTILE;
                    }
                }

                if (causa == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    type = HitType.EXPLOSION;
                    if (eev.getDamager() != null && eev.getDamager() instanceof Creeper) {
                        type = HitType.CREEPER;
                    }
                }
            }
            if (causa == EntityDamageEvent.DamageCause.POISON) {
                type = HitType.POISON;
            }
            if (causa == EntityDamageEvent.DamageCause.MAGIC) {
                type = HitType.POTION;
            }
            if (causa == EntityDamageEvent.DamageCause.LIGHTNING) {
                type = HitType.LIGHTING;
            }
            if (causa == EntityDamageEvent.DamageCause.STARVATION) {
                type = HitType.STARVATION;
            }
            if (causa == EntityDamageEvent.DamageCause.CONTACT) {
                type = HitType.CACTUS;
            }
            if (causa == EntityDamageEvent.DamageCause.THORNS) {
                type = HitType.THORNS;
            }
            if (causa == EntityDamageEvent.DamageCause.DROWNING) {
                type = HitType.DROWN;
            }
            if (causa == EntityDamageEvent.DamageCause.FIRE_TICK) {
                type = HitType.FIRE;
            }
            if (causa == EntityDamageEvent.DamageCause.FALL) {
                type = HitType.FALL;
            }
            if (causa == EntityDamageEvent.DamageCause.VOID) {
                type = HitType.VOID;
            }
            if (causa == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                type = HitType.FALLING_BLOCK;
            }
            if (causa == EntityDamageEvent.DamageCause.FIRE) {
                type = HitType.FIRE;
            }
            if (machucou == tomou) {
                machucou = null;
            }
            HitInfo info = new HitInfo(machucou, type, ev.getFinalDamage(), System.currentTimeMillis());
            info.setItem(item != null ? item.clone() : null);
            addHitInfo(tomou, info);
            if (type == HitType.ARROW || type == HitType.EGG || type == HitType.PROJECTILE || type == HitType.SNOW_BALL) {
                if (info.getPlayerDamager() != null && info.getPlayerDamager().isOnline()) {
                    info.getPlayerDamager().playSound(info.getPlayerDamager().getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
                }
            }

        }
    }


    @EventHandler
    public void death(PlayerDeathEvent ev) {
        ev.setDeathMessage(null);
        morre(ev.getEntity(), ev);
        scheduler().runAfter(() -> ev.getEntity().spigot().respawn(), 1);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void shoot(EntityShootBowEvent ev) {
        if (ev.getBow() != null) {
            ev.getProjectile().setMetadata("origem", new FixedMetadataValue(plugin(), ev.getBow()));
        }
    }


    protected void addHitInfo(Player p, HitInfo info) {
        List<HitInfo> lista;
        if (infos.containsKey(p.getUniqueId())) {
            lista = infos.get(p.getUniqueId());
        } else {
            lista = new ArrayList<>();
        }
        lista.add(info);
        infos.put(p.getUniqueId(), lista);
    }

    protected void addDeath(Player p) {
        List<HitInfo> lista;
        if (infos.containsKey(p.getUniqueId())) {
            lista = infos.get(p.getUniqueId());
        } else {
            lista = new ArrayList<>();
        }
        List<DeathInfo> mort = new ArrayList();
        if (mortes.containsKey(p.getUniqueId())) {
            mort = mortes.get(p.getUniqueId());
        }
        infos.put(p.getUniqueId(), new ArrayList());
        DeathInfo mor = new DeathInfo(lista);
        mort.add(mor);
        mortes.put(p.getUniqueId(), mort);
    }


    public static HitInfo getKiller(List<HitInfo> damage, long ms) {
        if (damage == null) {
            return null;
        }
        HitInfo last = null;
        for (HitInfo di : damage) {
            if (last == null || last.getTimestamp() < di.getTimestamp()) {
                if (di.getPlayerDamager() != null) {
                    last = di;
                }
            }
        }
        if (last != null) {
            if (last.getTimestamp() < (System.currentTimeMillis() - ms)) {
                last = null;
            }
        }
        return last;
    }


    public Player getKiller(Player p) {
        HitInfo lastdamage = getKiller(getLastDeath(p).getHits(), DAMAGE_DECAY);
        if (lastdamage == null) {
            return null;
        }
        return lastdamage.getPlayerDamager();
    }

    public DeathInfo getLastDeath(Player p) {
        if (mortes.containsKey(p.getUniqueId())) {
            DeathInfo last = null;
            for (DeathInfo mort : mortes.get(p.getUniqueId())) {
                if (last == null || last.quando < mort.quando) {
                    last = mort;
                }
            }
            return last;
        }
        return null;
    }

    @EventHandler
    public void move(PlayerMoveEvent ev) {
        if (ev.getTo().getY() <= -5) {
            if (!voidDamageCD.isCooldown(ev.getPlayer().getUniqueId())) {
                voidDamageCD.addCooldown(ev.getPlayer().getUniqueId());
                ((CraftPlayer) ev.getPlayer()).getHandle().damageEntity(DamageSource.OUT_OF_WORLD, 4);
            }
        }
    }

    public HashMap<UUID, Double> getDamageAmountByPlayer(DeathInfo m, long ms) {
        HashMap<UUID, Double> danos = new HashMap();
        for (HitInfo dano : m.getHits()) {
            if (dano.isPlayerDamager()) {
                if ((System.currentTimeMillis() + (ms)) > dano.getTimestamp()) {
                    Player p = dano.getPlayerDamager();
                    if (!danos.containsKey(p.getUniqueId())) {
                        danos.put(p.getUniqueId(), dano.getAmount());
                    } else {
                        danos.put(p.getUniqueId(), danos.get(p.getUniqueId()) + dano.getAmount());
                    }
                }
            }
        }
        ArrayList<UUID> uids = new ArrayList<UUID>(danos.keySet());
        Collections.sort(uids, new Comparator<UUID>() {
            @Override
            public int compare(UUID o1, UUID o2) {
                if (danos.get(o1) > danos.get(o2)) {
                    return 1;
                } else if (danos.get(o2) > danos.get(o2)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        HashMap<UUID, Double> danostemp = new HashMap();
        for (UUID uid : uids) {
            danostemp.put(uid, danos.get(uid));
        }
        return danostemp;
    }

    public void morre(Player p, PlayerDeathEvent originalEvent) {
        p.playEffect(EntityEffect.HURT);
        addDeath(p);
        DeathInfo m = getLastDeath(p);
        p.closeInventory();
        HitInfo last = getKiller(m.getHits(), DAMAGE_DECAY);
        Player killer = null;
        if (last != null) {
            killer = last.getPlayerDamager();
        }
        Player assist = null;
        if (killer != null) {
            HashMap<UUID, Double> damages = getDamageAmountByPlayer(m, DAMAGE_DECAY);
            for (UUID uid : damages.keySet()) {
                if (killer.getUniqueId() != uid) {
                    if (Bukkit.getPlayer(uid) != null) {
                        assist = Bukkit.getPlayer(uid);
                        break;
                    }
                }
            }
        }
        PlayerCustomDeathEvent ev = new PlayerCustomDeathEvent(p, killer, assist, m);
        Bukkit.getPluginManager().callEvent(ev);
        if (ev.isKeepItems()) {
            originalEvent.setKeepInventory(true);
        }
        if (killer != null) {
            killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 1, 1);
        }


    }

}
