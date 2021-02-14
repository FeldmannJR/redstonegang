package dev.feldmann.redstonegang.wire.game.games.servers.survival.terrenos;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SurvivalTerrenosEntry extends ServerEntry {

    public SurvivalTerrenosEntry() {
        super(SurvivalTerrenos.class);
    }

    @Override
    public String getNome() {
        return "Survival Terrenos";
    }

    @Override
    public String getMapsFolder() {
        return null;
    }

    @Override
    public ItemStack getIcone() {
        return new ItemStack(Material.CHEST);
    }
}
