package dev.feldmann.redstonegang.wire.game.base.addons.server.land.properties;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum PlayerProperty {

    BUILD("Construir", new ItemStack(Material.STONE_SPADE), false),
    DOORS("Usar Portas", new ItemStack(Material.WOOD_DOOR), true),
    CHESTS("Abrir Baus/Fornalhas", new ItemStack(Material.CHEST), false),
    BUILDCLOSE("Construir Grudado", new ItemStack(Material.GLASS), true),
    ELEVATOR("Usar Elevador", new ItemStack(Material.ENDER_PORTAL_FRAME), true),
    MOBSPAWNERS("Usar Mobspawners", new ItemStack(Material.MOB_SPAWNER), true),;

    private String name;
    private ItemStack item;
    private boolean all;

    PlayerProperty(String name, ItemStack item, boolean all) {
        this.name = name;
        this.item = item;
        this.all = all;
    }

    public ItemStack getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public boolean isAll() {
        return all;
    }
}
