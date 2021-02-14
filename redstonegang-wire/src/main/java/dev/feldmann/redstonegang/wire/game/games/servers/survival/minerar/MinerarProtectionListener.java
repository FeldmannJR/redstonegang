package dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar;

import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class MinerarProtectionListener extends BaseListener {

    private MinerarAddon addon;

    public MinerarProtectionListener(MinerarAddon addon) {
        this.addon = addon;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void blockBreak(BlockBreakEvent ev) {
        if (isSafe(ev.getBlock().getLocation())) {
            C.error(ev.getPlayer(), "Você não pode quebrar blocos aqui!");
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void place(BlockPlaceEvent ev) {
        if (isSafe(ev.getBlock().getLocation())) {
            C.error(ev.getPlayer(), "Você não pode colocar blocos aqui!");
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void liquidFlow(BlockFromToEvent ev) {
        Material type = ev.getToBlock().getType();
        if (type == Material.WATER || type == Material.STATIONARY_WATER || type == Material.LAVA || type == Material.STATIONARY_LAVA) {
            if (isSafe(ev.getToBlock().getLocation()))
                ev.setCancelled(true);
        }
    }

    @EventHandler
    public void bucketEmpty(PlayerBucketEmptyEvent ev) {
        Block b = ev.getBlockClicked().getRelative(ev.getBlockFace());
        if (isSafe(b.getLocation())) {
            ev.setCancelled(true);
        }
    }

    public boolean isSafe(Location loc) {
        for (MinerarData value : addon.players.values()) {
            Hitbox hit = value.getProtectedArea();
            if (hit != null && hit.isInside(loc)) {
                return true;
            }
            hit = value.getAnotherProtectedArea();
            if (hit != null && hit.isInside(loc)) {
                return true;
            }
        }
        return false;
    }
}
