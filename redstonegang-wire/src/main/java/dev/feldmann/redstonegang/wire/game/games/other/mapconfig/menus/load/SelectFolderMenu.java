package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.load;

import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class SelectFolderMenu extends Menu {

    MapConfigGame map;

    public SelectFolderMenu(MapConfigGame game) {
        super("Escolher Pasta", 6);
        this.map = game;
        addButtons();
    }

    public void addButtons() {
        for (String folder : map.mapas().getFolders()) {
            int count = map.getMaps(folder).size();
            addNext(new Button(ItemBuilder.item(Material.CHEST).name("§e" + folder).lore("§fQuantidade: §7" + count).build()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    new SelectMapMenu(map, folder).open(p);
                }
            });

        }
    }

}
