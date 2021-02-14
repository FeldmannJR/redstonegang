package dev.feldmann.redstonegang.wire.game.base.objects;

import dev.feldmann.redstonegang.wire.game.base.Server;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveOption;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Essa classe contem informações 'publicas' do jogo, ou sejá sem precisar instanciar ele para pegar, exs Kits, Configs do mapa, Icones, Maximo de players etc
 */
public abstract class ServerEntry {

    Class<? extends Server> gameClass;

    public ServerEntry(Class<? extends Server> classe) {
        this.gameClass = classe;
    }

    public Class<? extends Server> getGameClass() {
        return gameClass;
    }

    public abstract String getNome();

    public abstract String getMapsFolder();

    public abstract ItemStack getIcone();

    public List<MapConfigEntry> getMapConfigs() {
        java.util.List<MapConfigEntry> entries = new ArrayList<>();
        entries.add(new MapConfigEntry("builders", SaveType.STRING, SaveOption.OPTIONAL));
        entries.add(new MapConfigEntry("time", SaveType.STRING, SaveOption.OPTIONAL));
        entries.add(new MapConfigEntry("spawn", SaveType.LOCATION, SaveOption.REQUIRED));
        entries.add(new MapConfigEntry("mapa", SaveType.REGION, SaveOption.REQUIRED));
        return entries;
    }
}
