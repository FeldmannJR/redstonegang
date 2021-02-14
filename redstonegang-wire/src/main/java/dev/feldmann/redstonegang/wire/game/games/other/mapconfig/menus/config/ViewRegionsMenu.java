package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config;

import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.Menu;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ViewRegionsMenu extends MultiplePageMenu<String> {

    MapConfigGame game;

    public ViewRegionsMenu(MapConfigGame game) {
        super("Regions");
        this.game = game;
    }


    @Override
    public Button getButton(String ob, int page) {
        return new Button(ItemBuilder.item(Material.BEACON).name("§e" + ob).lore("§fClique para teleportar!", "§cShift + Click para deletar!").build()) {
            @Override
            public void click(Player p, Menu m, ClickType click) {
                if (click.isShiftClick()) {
                    game.getConfigurando().getConfig().removeRegion(ob);
                    p.sendMessage("§cDeletado!");
                    loadPage(page);
                    return;
                }
                Vector v = game.getConfigurando().getConfig().getRegion(ob).getMin();
                p.teleport(new Location(game.getConfigurando().getWorld(), v.getX(), v.getY(), v.getZ()));
                p.sendMessage("Teleportado");

            }
        };

    }

    @Override
    public List<String> getAll() {
        return new ArrayList(game.getConfigurando().config.getKeysLocations());
    }
}
