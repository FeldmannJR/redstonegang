package dev.feldmann.redstonegang.wire.game.base.addons.server.tps.elevator;

import dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks.CustomBlocksAddon;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.CustomItem;
import dev.feldmann.redstonegang.wire.game.base.addons.both.customItems.ItemRarity;
import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ElevatorItem extends CustomItem {

    ElevatorAddon manager;

    public ElevatorItem(ElevatorAddon manager) {
        super(new ItemStack(
                        Material.ENDER_PORTAL_FRAME),
                "Elevador",
                "elevator",
                ItemRarity.PAID,
                "Um item de elevador!");
        this.manager = manager;
    }

    @Override
    public void interact(PlayerInteractEvent ev) {

    }


    @Override
    public boolean blockPlace(BlockPlaceEvent ev) {
        CustomBlocksAddon blocks = manager.a(CustomBlocksAddon.class);
        ElevatorBlock block = new ElevatorBlock();
        block.from(ev.getBlock());
        blocks.save(block);
        return true;
    }
}
