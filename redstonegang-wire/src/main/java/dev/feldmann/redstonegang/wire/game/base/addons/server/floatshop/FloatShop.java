package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop;

import dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop.menu.ShopMenu;

import java.util.List;
import java.util.UUID;

public class FloatShop {
    private int id;
    private UUID npcUUID;
    private String permission;
    private int linhas;
    List<FloatItem> itens;

    private ShopMenu menu;

    public boolean editing = false;

    public FloatShop(UUID npcUUID, String permission) {
        this(-1, npcUUID, permission, 3);
    }

    public FloatShop(int id, UUID npcUUID, String permission, int linhas) {
        this.id = id;
        this.npcUUID = npcUUID;
        this.permission = permission;
        this.linhas = linhas;
    }

    public boolean isSaved() {
        return id != -1;
    }

    public int getId() {
        return id;
    }

    public void setItens(List<FloatItem> itens) {
        this.itens = itens;
    }

    public UUID getNpcUUID() {
        return npcUUID;
    }

    public String getPermission() {
        return permission;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void saveItens(FloatShopAddon addon) {
        for (FloatItem it : itens) {
            addon.db.saveItem(it);
        }
    }

    public void addItem(FloatItem item) {
        this.itens.add(item);
        if (menu != null) menu.updateFloatItems();
    }

    public int getLinhas() {
        return linhas;
    }

    public List<FloatItem> getItens() {
        return itens;
    }

    public void setLinhas(int linhas) {
        this.linhas = linhas;
    }

    public ShopMenu getMenu(FloatShopAddon addon) {
        if (menu == null) {
            menu = new ShopMenu(addon, this);
        }
        return menu;
    }

    public boolean hasMenu() {
        return menu != null;
    }

    public boolean hasEmptySlot() {
        return this.getItens().size() < getLinhas() * 9;
    }
}
