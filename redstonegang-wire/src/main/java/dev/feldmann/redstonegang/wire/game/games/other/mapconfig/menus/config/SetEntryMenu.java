package dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config;

import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.MapConfigGame;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons.ButtonBlockEntry;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons.ButtonConfigEntry;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons.ButtonLocEntry;
import dev.feldmann.redstonegang.wire.game.games.other.mapconfig.menus.config.buttons.ButtonRegionEntry;
import dev.feldmann.redstonegang.wire.modulos.menus.Button;
import dev.feldmann.redstonegang.wire.modulos.menus.MultiplePageMenu;

import java.util.List;

public class SetEntryMenu extends MultiplePageMenu<MapConfigEntry> {


    MapConfigGame game;

    public SetEntryMenu(MapConfigGame game) {
        super("Setar");
        this.game = game;
    }


    @Override
    public Button getButton(MapConfigEntry ob, int page) {
        switch (ob.getTipo()) {
            case LOCATION:
                return new ButtonLocEntry(ob, game, page);
            case BLOCK:
                return new ButtonBlockEntry(ob, game, page);
            case REGION:
                return new ButtonRegionEntry(ob, game, page);
            case STRING:
                return new ButtonConfigEntry(ob, game, page);

        }
        return null;
    }

    @Override
    public List<MapConfigEntry> getAll() {
        return game.getEntries();
    }
}
