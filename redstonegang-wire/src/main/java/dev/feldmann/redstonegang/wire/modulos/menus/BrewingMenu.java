package dev.feldmann.redstonegang.wire.modulos.menus;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

public class BrewingMenu extends Menu {

    public BrewingMenu(String titulo) {
        super(titulo, 1);
    }

    @Override
    protected void createInventory() {
        this.inv = Bukkit.createInventory(null, InventoryType.BREWING, invTitle);
    }

    public void setTop(Button b) {
        add(3, b);
    }

    public void setLeft(Button b) {
        add(0, b);
    }

    public void setRight(Button b) {
        add(2, b);
    }

    public void setCenter(Button b) {
        add(1, b);
    }


}
