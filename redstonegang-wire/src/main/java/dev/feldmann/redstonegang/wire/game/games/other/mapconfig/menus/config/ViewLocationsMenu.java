package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config;

import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.List;

public class ViewLocationsMenu extends MultiplePageMenu<String> {

    MapConfigGame game;

    public ViewLocationsMenu(MapConfigGame game) {
        super("Locations");
        this.game = game;
    }


    @Override
    public Button getButton(String ob, int page) {
        boolean isBlock = game.isBlock(ob);

        return new Button(ItemBuilder.item(isBlock ? Material.STONE : Material.PAPER).name("§e" + ob).lore("§fClique para teleportar!", "§cShift+Click para deletar!").build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (click.isShiftClick()) {
                    game.getConfigurando().getConfig().removeLocation(ob);
                    p.sendMessage("§cDeletado!");
                    loadPage(page);
                    return;
                }

                p.teleport(game.getConfigurando().config.getWorldLocation(ob));
                p.sendMessage("Teleportado");
            }
        };

    }

    @Override
    public List<String> getAll() {
        return new ArrayList(game.getConfigurando().config.getKeysLocations());
    }
}
