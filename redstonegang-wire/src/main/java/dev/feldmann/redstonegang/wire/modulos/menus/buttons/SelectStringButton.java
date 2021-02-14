package dev.feldmann.redstonegang.wire.modulos.menus.buttons;

import dev.feldmann.redstonegang.wire.modulos.anvilgui.AnvilGUI;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class SelectStringButton extends Button {


    public SelectStringButton(ItemStack i) {
        super(i);
    }


    @Override
    public void click(Player pl, Menu m, ClickType click) {
        new AnvilGUI(pl, " ", (p, string) -> {
            accept(string.trim(), p);
            return null;
        }, 30);
    }

    public abstract void accept(String string, Player p);
}
