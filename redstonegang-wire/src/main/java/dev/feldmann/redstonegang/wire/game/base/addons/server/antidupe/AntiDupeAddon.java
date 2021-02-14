package dev.feldmann.redstonegang.wire.game.base.addons.server.antidupe;

import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.items.BlockUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class AntiDupeAddon extends Addon {

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void openHopper(PlayerInteractEvent ev) {
        if (ev.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (ev.getPlayer().isSneaking() && ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() != Material.AIR) {
                return;
            }
            if (ev.getClickedBlock().getType() == Material.HOPPER) {
                ev.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockDispensed(BlockDispenseEvent event) {
        if ((event.getItem().getType() == Material.LAVA) || (event.getItem().getType() == Material.LAVA_BUCKET)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void InterageCarro(PlayerInteractEntityEvent ev) {
        if (ev.getPlayer().isOp()) {
            return;
        }
        if (ev.getRightClicked().getType() == EntityType.MINECART_CHEST
                || ev.getRightClicked().getType() == EntityType.MINECART_FURNACE
                || ev.getRightClicked().getType() == EntityType.MINECART_HOPPER
                || ev.getRightClicked().getType() == EntityType.MINECART_MOB_SPAWNER
                || ev.getRightClicked().getType() == EntityType.MINECART_TNT
                || ev.getRightClicked().getType() == EntityType.MINECART_COMMAND) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent ev) {
        if (ev.getPlayer().getItemInHand() != null && ev.getPlayer().getItemInHand().getType() == Material.BOAT) {
            ev.setCancelled(true);
            return;
        }

        if (ev.hasBlock()) {
            if ((ev.getPlayer().isInsideVehicle())) {
                if (ev.getPlayer().getVehicle() instanceof Horse || ev.getPlayer() instanceof Boat) {
                    if (!BlockUtils.isUsable(ev.getClickedBlock().getType())) {
                        return;
                    }
                }
                C.error(ev.getPlayer(), "Você está em um veículo, saia dele para utilizar coisas.");
                ev.setCancelled(true);

            }
        }
    }

    @EventHandler
    public void PlayerBedEnder(PlayerBedEnterEvent ev) {
        ev.setCancelled(true);
        C.error(ev.getPlayer(), "Você não está com sono, vai minerar!");
    }

    @EventHandler
    public void entityinteract(PlayerInteractEntityEvent ev) {
        if (ev.getRightClicked() instanceof Boat) {
            ev.getRightClicked().remove();
            return;
        }
        if (ev.getRightClicked() instanceof Horse) {
            if (ev.getPlayer().getItemInHand() != null) {
                if (ev.getPlayer().getItemInHand().getType() == Material.CHEST) {
                    ev.setCancelled(true);
                }
                Horse h = (Horse) ev.getRightClicked();
                if (h.isCarryingChest() && !h.hasMetadata("NPC")) {
                    h.setCarryingChest(false);
                    ev.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void onRotationItemFrame(PlayerInteractEntityEvent ev) {
        Player p = ev.getPlayer();
        if (ev.getRightClicked() instanceof Hanging) {
            if (!p.isOp()) {
                if (p.isInsideVehicle()) {
                    C.error(ev.getPlayer(), "Você está em um veículo, saia dele para utilizar coisas.");
                    ev.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onChangeItemFrame(EntityDamageByEntityEvent ev) {
        if (ev.getDamager() instanceof Player) {
            Player p = (Player) ev.getDamager();
            Entity defender = ev.getEntity();
            if (defender instanceof ItemFrame) {
                if (!p.isOp()) {
                    if (p.isInsideVehicle()) {
                        C.error(p, "Você está em um veículo, saia dele para utilizar coisas.");
                        ev.setCancelled(true);
                    }
                }
            }
        }
    }

    // Fixes Bug WorldGuard ChuckUnload
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageFrame(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Projectile) {
            if (((Projectile) event.getDamager()).getShooter() instanceof Entity) {
                Entity attacker = (Entity) ((Projectile) event.getDamager()).getShooter();
                Entity defender = event.getEntity();
                if (defender instanceof ItemFrame) {
                    if ((attacker == null) || (attacker.getWorld() == null)) {
                        event.setDamage(0.0);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onStructureGrowEvent(StructureGrowEvent event) {
        for (BlockState blockState : event.getBlocks()) {
            if ((blockState.getBlock().getType() == Material.SIGN) || (blockState.getBlock().getType() == Material.SIGN_POST) || (blockState.getBlock().getType() == Material.WALL_SIGN)) {
                if (event.getPlayer() != null) {
                    C.error(event.getPlayer(), "Sua Arvore está conflitando com outros blocos!");
                }
                event.setCancelled(true);
            }
        }
    }
}
