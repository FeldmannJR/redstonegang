package dev.feldmann.redstonegang.wire.game.base.controle.menus;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.Games;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class TrocaGameMenu extends Menu {

    public TrocaGameMenu() {
        super("Troca game", 6);
        trocaButtons();
    }

    private void trocaButtons() {

        for (Games g : Games.values()) {
            addNext(new Button(ItemBuilder.item(g.getEntry().getIcone()).name("§f" + g.name()).build()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    Wire.instance.game().loadGame(g.getEntry());
                }
            });
        }
    }
}
