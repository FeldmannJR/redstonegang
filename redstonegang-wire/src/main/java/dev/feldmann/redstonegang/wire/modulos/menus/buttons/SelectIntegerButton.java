package dev.feldmann.redstonegang.wire.modulos.menus.buttons;

import dev.feldmann.redstonegang.wire.modulos.anvilgui.AnvilGUI;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class SelectIntegerButton extends Button {
    int min = Integer.MIN_VALUE;
    int max = Integer.MAX_VALUE;
    String insert;

    public SelectIntegerButton(ItemStack i) {
        this(i, "");
    }

    public SelectIntegerButton(ItemStack i, String insert) {
        super(i);
        this.insert = insert;
    }
    public SelectIntegerButton(ItemStack i, String insert,int min) {
        super(i);
        this.insert = insert;
        this.min = min;
    }

    public SelectIntegerButton(ItemStack i, int min, int max) {
        super(i);
        this.min = min;
        this.max = max;
    }

    public SelectIntegerButton(ItemStack i, int min) {
        super(i);
        this.min = min;
    }

    @Override
    public void click(Player pl, Menu m, ClickType click) {
        new AnvilGUI(pl, insert, (p, string) -> {
            int preco = -1;
            try {
                preco = Integer.valueOf(string);
            } catch (NumberFormatException ex) {
                C.error(p, "§cValor inválido!");
                accept(null, pl);
                return null;
            }

            if (preco >= min && preco < max) {
                accept(preco, p);
                return null;
            } else {
                C.error(p, "§cValor inválido!");
                accept(null, p);
                return null;
            }
        }, 30);
    }

    public abstract void accept(Integer integer, Player p);
}
