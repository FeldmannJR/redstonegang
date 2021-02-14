package dev.feldmann.redstonegang.wire.game.games.other.mapconfig;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MapConfigGameEntry extends ServerEntry {

    public MapConfigGameEntry() {
        super(MapConfigGame.class);
    }

    @Override
    public String getMapsFolder() {
        return null;
    }

    @Override
    public ItemStack getIcone() {
        return new ItemStack(Material.WOOD_AXE);
    }


    @Override
    public String getNome() {
        return "Configurador de Mapas";
    }
}
