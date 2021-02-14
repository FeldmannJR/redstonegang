package dev.feldmann.redstonegang.wire.game.games.other.hub;

import dev.feldmann.redstonegang.wire.game.base.Games;
import dev.feldmann.redstonegang.wire.game.base.objects.MinigameEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.MapConfigEntry;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveOption;
import dev.feldmann.redstonegang.wire.game.base.objects.maps.SaveType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HubEntry extends ServerEntry {
    public HubEntry() {
        super(Hub.class);
    }

    @Override
    public String getMapsFolder() {
        return "HUB";
    }

    @Override
    public ItemStack getIcone() {
        return new ItemStack(Material.ENDER_PORTAL_FRAME);
    }


    @Override
    public String getNome() {
        return "Hub";
    }

    @Override
    public List<MapConfigEntry> getMapConfigs() {
        List<MapConfigEntry> conf = super.getMapConfigs();
        for (Games g : Games.values()) {
            if (g.getEntry() instanceof MinigameEntry) {
                conf.add(new MapConfigEntry("npc_" + g.name(), SaveType.LOCATION, SaveOption.REQUIRED, "Npc do minigame " + g.name()));
            }
        }
        return conf;
    }

}
