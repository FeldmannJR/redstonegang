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

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * Skype: junior.feldmann GitHub: https://github.com/feldmannjr Facebook:
 * https://www.facebook.com/carlosandre.feldmannjunior
 *
 * @author Feldmann
 */
public abstract class Button {

    ItemStack item;
    private int slot = -1;
    Menu menu = null;


    public void playSound(Player p, Sound s, float pitch) {
        p.playSound(p.getLocation(), s, 1, pitch);
    }

    public void setItemStack(ItemStack it) {
        this.item = it;
        if (menu != null) {
            menu.updateButton(this);
        }

    }


    public ItemStack getItem() {
        return item;
    }

    public void playSound(Player p, Sound s) {
        playSound(p, s, 1);
    }

    public Button(ItemStack i) {
        this.item = i;
    }


    public int getSlot() {
        return slot;
    }

    protected void setSlot(int slot) {
        this.slot = slot;
    }

    public abstract void click(Player p, Menu m, ClickType click);
}
