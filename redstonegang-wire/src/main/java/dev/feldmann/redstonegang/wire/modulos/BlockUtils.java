package dev.feldmann.redstonegang.wire.modulos;

import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.utils.MetaUtils;

import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.spigotmc.redstonegang.event.BlockToFallingBlockEvent;

public class BlockUtils extends Modulo implements Listener {
    @Override
    public void onEnable() {
        register(this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void blockPlace(BlockPlaceEvent ev) {
        MetaUtils.set(ev.getBlock(), "playerPlaced", ev.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void blockBreak(BlockBreakEvent ev) {
        MetaUtils.remove(ev.getBlock(), "playerPlaced");
    }

    public static boolean isPlayerPlaced(Block b) {
        return MetaUtils.get(b, "playerPlaced") != null;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void transform(BlockToFallingBlockEvent ev) {
        FallingBlock falling = ev.getFalling();
        if (isPlayerPlaced(ev.getBlock())) {
            MetaUtils.set(ev.getFalling(), "playerPlaced", MetaUtils.get(ev.getBlock(), "playerPlaced"));
        }
        MetaUtils.remove(ev.getBlock(), "playerPlaced");
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void back(EntityChangeBlockEvent ev) {
        if (ev.getEntity() instanceof FallingBlock) {
            Object playerPlaced = MetaUtils.get(ev.getEntity(), "playerPlaced");
            if (playerPlaced != null) {
                MetaUtils.set(ev.getBlock(), "playerPlaced", playerPlaced);
            }
        }
    }
}
