package dev.feldmann.redstonegang.wire.game.games.other.espera;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveOption;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EsperaEntry extends ServerEntry {
    public EsperaEntry() {
        super(Espera.class);
    }

    @Override
    public String getMapsFolder() {
        return "espera";
    }

    @Override
    public ItemStack getIcone() {
        return new ItemStack(Material.ANVIL);
    }

    @Override
    public List<MapConfigEntry> getMapConfigs() {
        List<MapConfigEntry> confs = super.getMapConfigs();
        confs.add(new MapConfigEntry("block", SaveType.BLOCK, SaveOption.SEQUENTIAL, "Blocos de anims"));
        confs.add(new MapConfigEntry("anims", SaveType.LOCATION, SaveOption.SEQUENTIAL, "Blocos de anims"));
        return confs;
    }


    @Override
    public String getNome() {
        return "Espera";
    }
}
