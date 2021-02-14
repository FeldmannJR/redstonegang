package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons;

import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.HitInfo;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.HitType;
import dev.feldmann.redstonegang.wire.game.base.addons.both.damageinfo.PlayerCustomDeathEvent;
import dev.feldmann.redstonegang.wire.game.base.addons.both.stats.*;
import dev.feldmann.redstonegang.wire.game.base.addons.both.stats.*;
import dev.feldmann.redstonegang.wire.game.base.addons.both.stats.events.PlayerPlayedTimeUpdateEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.game.base.objects.annotations.Dependencies;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar.SurvivalMinerar;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.terrenos.SurvivalTerrenos;
import dev.feldmann.redstonegang.wire.modulos.BlockUtils;
import dev.feldmann.redstonegang.wire.utils.location.LocUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.MaterialData;

@Dependencies(hard = StatAddon.class)
public class SurvivalStats extends Addon {


    @EventHandler
    public void gainMinutes(PlayerPlayedTimeUpdateEvent ev) {
        Stats.SURV_MINUTES_ONLINE.addPoints(getPlayerId(ev.getPlayer()), ev.getMinutes());
        if (getServer() instanceof SurvivalTerrenos) {
            Stats.SURV_MINUTES_ONLINE_TERRENOS.addPoints(getPlayerId(ev.getPlayer()), ev.getMinutes());
        }
        if (getServer() instanceof SurvivalMinerar) {
            Stats.SURV_MINUTES_ONLINE_TERRENOS.addPoints(getPlayerId(ev.getPlayer()), ev.getMinutes());
        }
    }


    @EventHandler
    public void killMob(EntityDeathEvent ev) {
        if (ev.getEntity().getKiller() != null) {
            if (ev.getEntity().getKiller() instanceof Player) {
                Player killer = ev.getEntity().getKiller();
                PlayerStat stat = extractStatFromEntity(ev.getEntity());
                if (stat != null) {
                    stat.addPoints(getPlayerId(killer), 1);
                }
            }

        }
    }


    private PlayerStat extractStatFromEntity(Entity ent) {
        EntityType type = ent.getType();
        if (type == EntityType.SKELETON) {
            if (((Skeleton) ent).getSkeletonType() == Skeleton.SkeletonType.WITHER) {
                return Stats.SURV_KILL_WITHER_SKELETON;
            }
        }
        if (type == EntityType.CREEPER) {
            if (((Creeper) ent).isPowered()) {
                return Stats.SURV_KILL_CHARGED_CREEPER;
            }
        }

        if (ent instanceof Slime) {
            Slime sm = (Slime) ent;
            if (sm.getSize() > 1) {
                return null;
            }
        }
        if (ent instanceof Player) {
            return null;
        }

        for (PlayerStat st : a(StatAddon.class).getStats()) {
            if (st instanceof PlayerStatKill) {
                if (st.getUniqueId().startsWith("surv_")) {
                    if (((PlayerStatKill) st).types != null) {
                        for (EntityType tp : ((PlayerStatKill) st).types) {
                            if (tp == type) {
                                return st;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @EventHandler
    public void move(PlayerMoveEvent ev) {
        if (!LocUtils.isSameBlock(ev.getFrom(), ev.getTo())) {
            Stats.SURV_BLOCKS_WALKED.addPoints(getPlayerId(ev.getPlayer()), 1);
        }
    }

    @EventHandler
    public void death(PlayerCustomDeathEvent ev) {
        if (ev.getKiller() != null) {
            if (ev.getKiller().isOnline()) {
                Stats.SURV_DEATH_PLAYER.addPoints(getPlayerId(ev.getPlayer()), 1);
                return;
            }
        }

        HitInfo lh = ev.getInfo().getLastHit();
        if (lh != null) {
            if (lh.getType() == HitType.MOB || lh.getType() == HitType.CREEPER || lh.getDamager() instanceof Creature) {
                Stats.SURV_DEATH_MOBS.addPoints(getPlayerId(ev.getPlayer()), 1);
                return;
            }
        }
        Stats.SURV_DEATH_OTHER.addPoints(getPlayerId(ev.getPlayer()), 1);

    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void breakBlock(BlockBreakEvent ev) {
        PlayerStatBreak br = extractFromBlock(ev.getBlock());
        if (br != null) {
            boolean playerPlacedIgnore = false;
            if (br == Stats.SURV_BREAK_CARROT || br == Stats.SURV_BREAK_POTATO || br == Stats.SURV_BREAK_WHEAT || br == Stats.SURV_BREAK_NETHERWART) {
                playerPlacedIgnore = true;
            }
            if (playerPlacedIgnore || !BlockUtils.isPlayerPlaced(ev.getBlock())) {
                br.addPoints(getPlayerId(ev.getPlayer()), 1);
            }
        }

    }

    public int praCima(Block b, Material m) {
        int x = 0;
        while (b.getType() == m) {
            if (!BlockUtils.isPlayerPlaced(b)) {
                x++;
            }
            b = b.getRelative(BlockFace.UP);
        }

        return x;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void breakCanaOrCactus(BlockBreakEvent ev) {
        Material m = ev.getBlock().getType();
        if (m == Material.SUGAR_CANE_BLOCK || m == Material.CACTUS) {
            int xp = praCima(ev.getBlock(), m);
            if (xp >= 1) {
                PlayerStat st = Stats.SURV_BREAK_CACTUS;
                if (m == Material.SUGAR_CANE_BLOCK) {
                    st = Stats.SURV_BREAK_SUGAR_CANE;
                }
                st.addPoints(getPlayerId(ev.getPlayer()), xp);
            }
        }
    }


    public PlayerStatBreak extractFromBlock(Block b) {
        MaterialData data = b.getState().getData();
        for (PlayerStat st : a(StatAddon.class).getStats()) {
            if (st instanceof PlayerStatBreak) {
                if (st.getUniqueId().startsWith("surv_")) {
                    MaterialData[] d = ((PlayerStatBreak) st).data;
                    if (d != null) {
                        for (MaterialData i : d) {
                            if (i.equals(data)) {
                                return (PlayerStatBreak) st;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

}
