package dev.feldmann.redstonegang.wire.game.base.addons.server.land.listener;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.Land;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandFlags;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties.PlayerProperty;
import dev.feldmann.redstonegang.wire.utils.EntityUtils;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandConfig;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.*;

public class ProtectionListener extends TerrenoListener {

    public ProtectionListener(LandAddon manager) {
        super(manager);
    }

    /*
     *  Proteção contra players
     *
     *
     * */
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerArmorStandManipulateEvent event) {
        if (event.getRightClicked() instanceof ArmorStand) {
            if (byPass(event.getPlayer())) {
                return;
            }
            if (!can(event.getPlayer(), event.getRightClicked(), PlayerProperty.BUILD, true)) {
                event.setCancelled(true);
                sendDeny(event.getPlayer());
            }

        }
    }

    @EventHandler
    public void protectArmroStand(EntityDamageByEntityEvent ev) {
        if (ev.getEntity() instanceof ArmorStand) {
            Land t = getTerreno(ev.getEntity());
            if (t != null) {
                if (ev.getDamager() instanceof Player) {
                    if (byPass((Player) ev.getDamager())) {
                        return;
                    }
                    Player p = (Player) ev.getDamager();
                    if (!can(p, t, PlayerProperty.BUILD)) {
                        ev.setCancelled(true);
                        sendDeny(p);
                    }
                } else {
                    ev.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void protectPlaceHanigng(HangingPlaceEvent ev) {
        if (byPass(ev.getPlayer())) {
            return;
        }
        if (!can(ev.getPlayer(), ev.getBlock(), PlayerProperty.BUILD, false)) {
            ev.setCancelled(true);
            sendDeny(ev.getPlayer());
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerBucketEmpty(PlayerBucketEmptyEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        if (byPass(ev.getPlayer())) {
            return;
        }
        Block b = ev.getBlockClicked().getRelative(ev.getBlockFace());
        Land t = getTerreno(b);
        if (t == null) {
            if (byPassWild(ev.getPlayer())) {
                return;
            }
        } else {
            if (can(ev.getPlayer(), t, PlayerProperty.BUILD)) {
                return;
            }
        }
        sendDeny(ev.getPlayer());
        ev.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void PlayerBucketFill(PlayerBucketFillEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        if (byPass(ev.getPlayer())) {
            return;
        }
        Block b = ev.getBlockClicked();
        Land t = getTerreno(b);
        if (t == null) {
            if (byPassWild(ev.getPlayer())) {
                return;
            }
        } else {
            if (can(ev.getPlayer(), t, PlayerProperty.BUILD)) {
                return;
            }
        }
        sendDeny(ev.getPlayer());
        ev.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void interact(PlayerInteractEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        if (byPass(ev.getPlayer())) {
            return;
        }

        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block b = ev.getClickedBlock().getRelative(ev.getBlockFace());
            if (ev.getPlayer().getItemInHand() != null && LandConfig.rightClickItem.contains(ev.getPlayer().getItemInHand().getType())) {
                Land t = getTerreno(b);
                if (t == null) {
                    if (byPassWild(ev.getPlayer())) {
                        return;
                    }
                } else {
                    if (can(ev.getPlayer(), t, PlayerProperty.BUILD)) {
                        return;
                    }

                }
                sendDeny(ev.getPlayer(), "usar este item");
                ev.setCancelled(true);

            }

        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void InteractBlockEvent(PlayerInteractEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        if (!ev.hasBlock()) {
            return;
        }
        if (byPass(ev.getPlayer())) {
            return;
        }
        if (ev.getAction() == Action.PHYSICAL && ev.getClickedBlock().getType() == Material.SOIL) {
            ev.setCancelled(true);
            return;
        }
        if (ev.getClickedBlock().getLocation().getBlockY() == 0) {
            return;
        }

        Land t = getTerreno(ev.getClickedBlock());
        if (t != null) {
            if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Material type = ev.getClickedBlock().getType();
                if (LandConfig.rightClickBlock.contains(type)) {
                    PlayerProperty prop = PlayerProperty.BUILD;
                    if (LandConfig.doors.contains(type)) {
                        prop = PlayerProperty.CHESTS;
                    } else if (LandConfig.chests.contains(type)) {
                        prop = PlayerProperty.CHESTS;
                    }
                    if (can(ev.getPlayer(), t, prop)) {
                        return;
                    }
                    sendDeny(ev.getPlayer(), "usar este bloco");
                    ev.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onRotationItemFrame(PlayerInteractEntityEvent ev) {
        Player p = ev.getPlayer();
        if (ev.getRightClicked() instanceof Hanging) {
            if (!can(p, ev.getRightClicked(), PlayerProperty.BUILD, true)) {
                ev.setCancelled(true);
                sendDeny(ev.getPlayer());

            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChangeItemFrame(EntityDamageByEntityEvent ev) {
        if (ev.getDamager() instanceof Projectile) {
            Entity defender = ev.getEntity();
            if (defender instanceof ItemFrame) {
                ev.setCancelled(true);
                return;
            }
        }
        if (ev.getDamager() instanceof Player) {
            Player p = (Player) ev.getDamager();
            if (byPass(p)) {
                return;
            }
            Entity defender = ev.getEntity();
            if (defender instanceof ItemFrame) {
                if (!can(p, defender, PlayerProperty.BUILD, true)) {
                    ev.setCancelled(true);
                    sendDeny(p);

                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPaintingBreak(HangingBreakByEntityEvent ev) {
        if (ev.getRemover() instanceof Player) {
            Player p = (Player) ev.getRemover();
            if (byPass(p)) {
                return;
            }
            if (can(p, ev.getEntity(), PlayerProperty.BUILD, true)) {
                return;
            }
            sendDeny(p);
        }
        ev.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPaintingBreak(HangingBreakEvent ev) {
        if (ev.getCause() == HangingBreakEvent.RemoveCause.EXPLOSION) {
            ev.setCancelled(true);
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void BreakEvent(BlockBreakEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        //Op fodão foda-se tudo
        if (byPass(ev.getPlayer())) {
            return;
        }

        Land t = getTerreno(ev.getBlock());
        //Se não tem terreno
        if (t == null) {
            //Permissão especial pra poder construir onde não tem
            if (byPassWild(ev.getPlayer())) {
                return;
            }
            //Se for algum material especifico
            if (LandConfig.nearbyAllow.contains(ev.getBlock().getType())) {
                //E se estiver próximo de um terreno do nego ele deixa
                if (manager().isNearby(RedstoneGang.getPlayer(ev.getPlayer().getUniqueId()).getId(), ev.getBlock().getX(), ev.getBlock().getZ(), LandAddon.ESPACO_ENTRE, ev.getPlayer().getWorld().getName())) {
                    return;
                }
            }

        } else {
            if (can(ev.getPlayer(), t, PlayerProperty.BUILD)) {
                return;
            }
        }
        sendDeny(ev.getPlayer());
        ev.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void PlaceEvent(BlockPlaceEvent ev) {
        if (ev.isCancelled()) {
            return;
        }
        //Op fodão foda-se tudo
        if (byPass(ev.getPlayer())) {
            return;
        }
        Land t = getTerreno(ev.getBlock());
        //Se não tem terreno
        if (t == null) {
            //Permissão especial pra poder construir onde não tem
            if (byPassWild(ev.getPlayer())) {
                return;
            }
        } else {
            if (can(ev.getPlayer(), t, PlayerProperty.BUILD)) {
                return;
            }
        }
        sendDeny(ev.getPlayer());
        ev.setCancelled(true);
    }

    /*
     * Proteção contra outros
     * */
    @EventHandler
    public void change(EntityChangeBlockEvent ev) {
        if (getTerreno(ev.getBlock()) != null) {
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void spawn(CreatureSpawnEvent ev) {
        Land terreno = getTerreno(ev.getEntity());
        if (terreno != null) {
            EntityType type = ev.getEntityType();
            if (EntityUtils.isAgressive(type) && ev.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER && ev.getSpawnReason() != CreatureSpawnEvent.SpawnReason.EGG) {
                boolean spawn = terreno.getFlags().get(LandFlags.SPAWN_MOBS);
                if (!spawn) ev.setCancelled(true);
            }
        }
    }


}
