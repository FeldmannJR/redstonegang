package dev.feldmann.redstonegang.wire.modulos.menus;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

public class Menu3x3 extends Menu {

    public Menu3x3(String titulo) {
        super(titulo, 1);
    }

    @Override
    protected void createInventory() {
        this.inv = Bukkit.createInventory(null, InventoryType.DISPENSER, "§l" + invTitle);
    }

}
