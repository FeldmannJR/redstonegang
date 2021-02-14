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
import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Skype: junior.feldmann GitHub: https://github.com/feldmannjr Facebook:
 * https://www.facebook.com/carlosandre.feldmannjunior
 *
 * @author Feldmann
 */
public class MenuManager extends Modulo implements Listener {


    private Cooldown clickCd = new Cooldown(333);

    @Override
    public void onEnable() {
        register(this);
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void click(InventoryClickEvent ev) {
        if (ev.getInventory() != null) {
            Player p = (Player) ev.getWhoClicked();
            if (InventoryVariables.getObject(ev.getInventory(), "menu") != null) {
                ((Menu) InventoryVariables.getObject(ev.getInventory(), "menu")).clickInventory(ev);

            }
        }
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.TICK) {
            onTick();
        }

    }

    public void onTick() {
        List<Menu> ms = new ArrayList();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Menu m = getOpenMenu(p);
            if (m == null) continue;
            if (!ms.contains(m)) {
                m.onTick();
                m.getButtons().stream().filter((b) -> b instanceof ButtonTick).forEach((b) -> {
                    ((ButtonTick) b).onTick();
                });
                ms.add(m);
            }

        }

    }

    public static Menu getOpenMenu(HumanEntity p) {
        if (p.getOpenInventory() != null && p.getOpenInventory().getTopInventory() != null) {
            Inventory i = p.getOpenInventory().getTopInventory();
            if (InventoryVariables.getObject(i, "menu") != null) {

                Menu m = (Menu) InventoryVariables.getObject(i, "menu");
                return m;

            }
        }
        return null;

    }

    @EventHandler
    public void click(InventoryCloseEvent ev) {
        if (ev.getInventory() != null) {
            if (InventoryVariables.getObject(ev.getInventory(), "menu") != null) {
                Menu m = (Menu) InventoryVariables.getObject(ev.getInventory(), "menu");
                m.closeInventory(ev);
            }
        }
    }
}
