package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaItem;
import dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops.LojaNPC;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.buttons.DummyButton;

public class LojaMenu extends Menu {

    public LojaMenu(LojaNPC loja) {
        super(loja.getNPC().getName(), loja.getSize());
        for (LojaItem it : loja.getItens()) {
            if (it.getPrecoCompra() == null && it.getPrecoVenda() == null) {
                add(it.getSlot(), new DummyButton(it.getItem()));
            } else {
                add(it.getSlot(), new ItemButton(it));
            }
        }
    }
}
