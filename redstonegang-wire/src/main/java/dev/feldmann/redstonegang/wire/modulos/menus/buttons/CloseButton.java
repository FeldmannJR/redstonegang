package dev.feldmann.redstonegang.wire.modulos.menus.buttons;

import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class CloseButton extends Button {
    public CloseButton() {
        super(ItemBuilder.item(Material.BARRIER).lore(C.itemDesc("Fechar menu")).name(C.item("Fechar")).build());
    }


    @Override
    public void click(Player p, Menu m, ClickType click) {
        p.closeInventory();
    }
}
