package dev.feldmann.redstonegang.wire.modulos.menus.buttons;

import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import org.bukkit.Material;

public abstract class BackButton extends Button {
    public BackButton() {
        super(ItemBuilder.item(Material.DIODE).name(C.item("Voltar")).build());
    }

    public BackButton(String desc) {
        super(ItemBuilder.item(Material.DIODE).lore(C.itemDesc(desc)).name(C.item("Voltar")).build());
    }

}
