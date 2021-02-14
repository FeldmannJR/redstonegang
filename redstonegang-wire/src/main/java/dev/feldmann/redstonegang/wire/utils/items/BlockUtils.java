package dev.feldmann.redstonegang.wire.utils.items;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

public class BlockUtils {

    private static final List<Material> USABLE = Arrays.asList(
            Material.CHEST,
            Material.TRAPPED_CHEST,
            Material.ENDER_CHEST,
            Material.TRAP_DOOR,
            Material.ACACIA_DOOR,
            Material.BIRCH_DOOR,
            Material.DARK_OAK_DOOR,
            Material.JUNGLE_DOOR,
            Material.SPRUCE_DOOR,
            Material.WOOD_DOOR,
            Material.WOODEN_DOOR,
            Material.DISPENSER,
            Material.DROPPER,
            Material.BREWING_STAND,
            Material.NOTE_BLOCK,
            Material.WORKBENCH,
            Material.FURNACE,
            Material.BURNING_FURNACE,
            Material.LEVER,
            Material.STONE_BUTTON,
            Material.WOOD_BUTTON,
            Material.DIODE_BLOCK_ON,
            Material.DIODE_BLOCK_ON,
            Material.FENCE_GATE,
            Material.ACACIA_FENCE_GATE,
            Material.BIRCH_FENCE_GATE,
            Material.DARK_OAK_FENCE_GATE,
            Material.JUNGLE_FENCE_GATE,
            Material.SPRUCE_FENCE_GATE,
            Material.ENCHANTMENT_TABLE,
            Material.ANVIL,
            Material.HOPPER,
            Material.DROPPER,
            Material.BEACON
    );

    public static boolean isUsable(Material mat) {
        return USABLE.contains(mat);
    }
}
