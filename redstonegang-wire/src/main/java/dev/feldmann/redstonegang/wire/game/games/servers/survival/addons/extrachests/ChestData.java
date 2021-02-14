package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests;

import org.bukkit.inventory.ItemStack;

public class ChestData {

    private ChestMenu m;
    private int pId;
    private ChestList type;
    private ItemStack[] itens;
    public ExtraChests manager;

    public ChestData(int pId, ChestList type, ItemStack[] itens) {
        this.type = type;
        this.itens = itens;
        this.pId = pId;
    }

    public ItemStack[] getItens() {
        return itens;
    }

    public ChestMenu getMenu() {
        if (m == null) {
            m = new ChestMenu(this);
        }
        return m;
    }

    public boolean hasMenu() {
        return m != null;
    }

    public ExtraChests getManager() {
        return manager;
    }

    public int getpId() {
        return pId;
    }

    public ChestList getType() {
        return type;
    }

    public void resetMenu() {
        m = null;
    }
}
