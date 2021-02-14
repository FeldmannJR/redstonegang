package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.load;

import dev.feldmann.redstonegang.common.api.maps.responses.MapResponse;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class SelectMapMenu extends Menu {

    String folder;
    private MapConfigGame game;

    public SelectMapMenu(MapConfigGame game, String folder) {
        super("Escolher Mapa " + folder, 6);
        this.folder = folder;
        this.game = game;
        addButtons();
    }

    public void addButtons() {
        for (MapResponse map : game.getMaps(folder)) {
            addNext(new Button(ItemBuilder.item(Material.GRASS).name("§e" + map.name).lore("§fClique para carregar o", "§fmapa!", "§cOBS: Perde mapa atual se não for salvo!").build()) {
                @Override
                public void click(Player p, Menu m, ClickType click) {
                    game.setConfigurando(map);
                }
            });
        }
    }

}
