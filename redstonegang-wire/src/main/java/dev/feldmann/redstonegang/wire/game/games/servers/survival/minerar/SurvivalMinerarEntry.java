package dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SurvivalMinerarEntry extends ServerEntry {

    public SurvivalMinerarEntry() {
        super(SurvivalMinerar.class);
    }

    @Override
    public String getNome() {
        return "Survival Minerar";
    }

    @Override
    public String getMapsFolder() {
        return null;
    }

    @Override
    public ItemStack getIcone() {
        return new ItemStack(Material.IRON_PICKAXE);
    }
}
