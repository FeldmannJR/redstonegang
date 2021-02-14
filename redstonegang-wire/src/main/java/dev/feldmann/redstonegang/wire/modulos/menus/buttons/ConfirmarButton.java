package dev.feldmann.redstonegang.wire.modulos.menus.buttons;

import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.menus.ConfirmarMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public abstract class ConfirmarButton extends Button {

    String oq;
    String desc;

    public ConfirmarButton(ItemStack i, String oq, String desc) {
        super(i);
        this.oq = oq;
        this.desc = desc;
    }

    @Override
    public void click(Player p, Menu m, ClickType click) {
        ConfirmarButton b = this;
        ConfirmarMenu menu = new ConfirmarMenu(getItem(), oq, desc) {
            @Override
            public void confirmar(Player p) {
                b.confirmar(p);
            }

            @Override
            public void recusar(Player p) {
                b.recusar(p);
            }
        };
        menu.open(p);
    }


    public abstract void confirmar(Player p);

    public abstract void recusar(Player p);

}
