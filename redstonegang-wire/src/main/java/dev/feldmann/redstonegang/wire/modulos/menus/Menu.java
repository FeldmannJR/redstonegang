/*
 *  _   __   _   _____   _____       ___       ___  ___   _____
 * | | |  \ | | /  ___/ |_   _|     /   |     /   |/   | /  ___|
 * | | |   \| | | |___    | |      / /| |    / /|   /| | | |
 * | | | |\   | \___  \   | |     / / | |   / / |__/ | | | |
 * | | | | \  |  ___| |   | |    / /  | |  / /       | | | |___
 * |_| |_|  \_| /_____/   |_|   /_/   |_| /_/        |_| \_____|
 *
 * Projeto feito por Carlos Andre Feldmann Junior, Isaias Finger e Gabriel Augusto Souza
 */
package dev.feldmann.redstonegang.wire.modulos.menus;

import dev.feldmann.redstonegang.common.utils.Cooldown;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * Skype: junior.feldmann GitHub: https://github.com/feldmannjr Facebook:
 * https://www.facebook.com/carlosandre.feldmannjunior
 *
 * @author Feldmann
 */
public class Menu {

    private static Cooldown globalCd = new Cooldown(333);
    protected String invTitle;
    private int size;
    private HashMap<Integer, Button> buttons = new HashMap<>();
    private HashSet<Consumer<Player>> closes = new HashSet();
    protected Inventory inv;
    private boolean open = false;

    //Pode mexer nos itens que não são botoes a vontade
    private boolean moveItens = false;


    public void onTick() {

    }

    public void setMaxStackSize(int size) {
        inv.setMaxStackSize(size);
    }

    public int getSize() {
        return size;
    }

    public List<ItemStack> getNonButtonItems() {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (int x = 0; x < inv.getSize(); x++) {
            if (getButton(x) == null) {
                if (inv.getItem(x) != null) {
                    list.add(inv.getItem(x));
                }
            }
        }
        return list;
    }

    public void addItemStack(int slot, ItemStack it) {
        if (getButton(slot) == null) {
            inv.setItem(slot, it);
        }
    }

    public void addItemStack(ItemStack it) {
        if (it == null)
        {
            return;
        }
        for (int x = 0; x < inv.getSize(); x++) {
            if (inv.getItem(x) == null) {
                inv.setItem(x, it);
                break;
            }
        }
    }

    public void addItemStack(ItemStack[] its)
    {
        if (its == null)
        {
            return;
        }
        for (ItemStack item : its)
        {
            this.addItemStack(item);
        }
    }

    public void clearNonButtonItems() {
        for (int x = 0; x < inv.getSize(); x++) {
            if (getButton(x) == null) {
                if (inv.getItem(x) != null) {
                    inv.remove(inv.getItem(x));
                }
            }
        }
    }

    public Menu(String titulo, int linhas) {
        this.invTitle = titulo;
        if (linhas <= 6) {
            this.size = linhas * 9;
        } else {
            this.size = linhas;
        }
        createInventory();
        InventoryVariables.setObject(inv, "menu", this);
    }

    protected void createInventory() {
        inv = Bukkit.createInventory(null, size, "§l" + invTitle);
    }

    public boolean isMoveItens() {
        return this.moveItens;
    }

    public void setMoveItens(boolean moveItens) {
        this.moveItens = moveItens;
    }


    public void addClose(Consumer<Player> ev) {
        closes.add(ev);
    }

    public HashSet<Consumer<Player>> getCloses() {
        return closes;
    }


    public void addNext(Button mb) {
        addToSquare(0, size - 1, mb);
    }


    public void addToBorder(int inicia, int termina, Button but) {
        int slot = -1;
        for (int x : MenuHelper.buildHollowSquare(inicia, termina)) {
            if (getButton(x) == null) {
                slot = x;
                break;
            }
        }
        if (slot != -1) {
            add(slot, but);
        }

    }


    public void addToSquare(int inicia, int termina, Button but) {
        int slot = -1;
        for (int x : MenuHelper.buildSquare(inicia, termina)) {
            if (getButton(x) == null) {
                slot = x;
                break;
            }
        }
        if (slot != -1) {
            add(slot, but);
        }
    }

    /**
     * Adiciona um dummy button com o itemstack
     */
    public Button add(int slot, ItemStack but) {
        return add(slot, new DummyButton(but));
    }

    public Button add(int slot, Button but) {

        if (buttons.containsKey(slot)) {
            removeButton(slot);
        }
        buttons.put(slot, but);
        but.menu = this;
        but.setSlot(slot);
        inv.setItem(slot, but.item);
        return but;
    }

    public Button getButton(int slot) {
        return buttons.get(slot);
    }

    public Button getButton(int linha, int coluna) {
        return getButton((linha * 9) + coluna);
    }


    public void setSlot(Button but, int slot) {
        if (buttons.containsValue(but)) {
            inv.setItem(but.getSlot(), null);
            but.setSlot(slot);
            inv.setItem(but.getSlot(), but.item);
        }
    }

    public void removeAllButtons() {
        for (Button b : getButtons()) {
            inv.setItem(b.getSlot(), null);
            b.setSlot(-1);
        }
        buttons.clear();
        return;

    }

    public void removeButton(int slot) {
        inv.setItem(slot, null);
        if (getButton(slot) != null) {
            buttons.remove(slot);
        }
    }

    public void removeButton(Button button) {
        if (button.getSlot() >= 0)
            removeButton(button.getSlot());
    }


    public boolean useCooldown() {
        return true;
    }

    public void open(Player p) {
        open = true;
        p.openInventory(inv);
    }

    public HashSet<Button> getButtons() {
        return new HashSet<>(buttons.values());
    }

    protected void updateButton(Button botao) {
        inv.setItem(botao.getSlot(), botao.item);
    }

    public boolean clickInventory(InventoryClickEvent ev) {
        ev.setCancelled(true);
        Player p = (Player) ev.getWhoClicked();
        int rawslot = ev.getRawSlot();
        boolean isButton = false;
        Button but = buttons.get(rawslot);
        if (but != null) {
            try {
                if (!useCooldown() || !globalCd.isCooldown(p.getUniqueId())) {
                    but.click(p, this, ev.getClick());
                    if (useCooldown()) {
                        globalCd.addCooldown(p.getUniqueId());
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                C.error(p, "Ocorreu um erro!");
                p.closeInventory();
            }

        } else {
            if (isMoveItens()) {
                ev.setCancelled(false);
            }
        }

        return but != null;
    }

    public int getViewers() {
        return inv.getViewers().size();
    }

    public void closeAll() {
        List<HumanEntity> viewers = inv.getViewers();
        for (HumanEntity viewer : new ArrayList<HumanEntity>(viewers)) {
            viewer.closeInventory();
        }

    }

    public ItemStack[] getContents() {
        return inv.getContents();
    }

    public void closeInventory(InventoryCloseEvent ev) {
        if (open) {
            for (Consumer<Player> a : getCloses()) {
                a.accept((Player) ev.getPlayer());
            }
            open = false;
        }
    }

    public static int calcSize(int min) {
        if (min % 9 != 0) {
            min += 9 - (min % 9);
        }
        return min;

    }

    protected ItemStack getBorderItemStack() {
        return ItemBuilder.item(Material.STAINED_GLASS_PANE).name("").color(DyeColor.RED).build();
    }

    protected void addBorder() {
        for (int x : MenuHelper.buildHollowSquare(0, getSize() - 1)) {
            add(x, new DummyButton(getBorderItemStack()));
        }
    }

}
