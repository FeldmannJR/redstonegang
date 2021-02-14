package dev.feldmann.redstonegang.wire.modulos.menus.buttons;

import dev.feldmann.redstonegang.wire.modulos.anvilgui.AnvilGUI;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class SelectDoubleButton extends Button {
    double min = -Double.MAX_VALUE;
    double max = Double.MAX_VALUE;
    String insert;

    public SelectDoubleButton(ItemStack i) {
        this(i, "");
    }

    public SelectDoubleButton(ItemStack i, String insert) {
        super(i);
        this.insert = insert;
    }

    public SelectDoubleButton(ItemStack i, int min, int max) {
        super(i);
        this.min = min;
        this.max = max;
    }

    public SelectDoubleButton(ItemStack i, int min) {
        super(i);
        this.min = min;
    }

    @Override
    public void click(Player pl, Menu m, ClickType click) {
        new AnvilGUI(pl, insert, (p, string) -> {
            Double preco;
            try {
                preco = Double.valueOf(string);
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

    public abstract void accept(Double value, Player p);
}
