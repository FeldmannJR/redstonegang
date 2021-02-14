package dev.feldmann.redstonegang.wire.game.base.addons.server.kit.menus;

import dev.feldmann.redstonegang.wire.game.base.addons.server.kit.Kit;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;

public class MenuVerKit extends Menu {

    public MenuVerKit(Kit kit) {
        super("Ver Kit " + kit.getName(), 3);
    }
}
