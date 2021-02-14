package dev.feldmann.redstonegang.wire.game.base.addons.both.staffcommands;

import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.utils.EventUtils;
import dev.feldmann.redstonegang.wire.utils.items.BlockUtils;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class VanishFlagListener extends BaseListener {

    StaffCommandsAddon addon;

    public VanishFlagListener(StaffCommandsAddon addon) {
        this.addon = addon;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void chat(AsyncPlayerChatEvent ev) {
        if (shouldCancelEvent(ev.getPlayer(), VanishFlag.CHAT)) {
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void drop(PlayerDropItemEvent ev) {
        if (shouldCancelEvent(ev.getPlayer(), VanishFlag.DROP)) {
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void damage(EntityDamageByEntityEvent ev) {
        Player damager = EventUtils.getDamager(ev);
        if (damager != null) {
            if (shouldCancelEvent(damager, VanishFlag.DO_DAMAGE)) {
                ev.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void interact(PlayerInteractEvent ev) {
        if (ev.hasBlock()) {
            if (BlockUtils.isUsable(ev.getClickedBlock().getType()) || ev.getAction() == Action.PHYSICAL) {
                if (shouldCancelEvent(ev.getPlayer(), VanishFlag.INTERACT)) {
                    ev.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void pickupIte(PlayerPickupItemEvent ev) {
        if (shouldCancelEvent(ev.getPlayer(), VanishFlag.PICKUP)) {
            ev.setCancelled(true);
        }
    }

    private boolean shouldCancelEvent(Player p, VanishFlag flag) {
        if (addon.isVanished(p)) {
            if (!addon.getFlag(p, flag)) {
                if (flag.canSendMessage(p)) {
                    C.info(p, "Você está com o vanish ligado! Para usar %% ative em %cmd% !", flag.getAction(), "/vanishflags");
                }
                return true;
            }
        }
        return false;
    }
}
