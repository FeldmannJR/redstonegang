package dev.feldmann.redstonegang.wire.game.games.servers.worldgen;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WorldGenBlueprint extends ServerEntry {

    public WorldGenBlueprint() {
        super(WorldGen.class);
    }

    @Override
    public String getNome() {
        return "WorldGen";
    }

    @Override
    public String getMapsFolder() {
        return null;
    }

    @Override
    public ItemStack getIcone() {
        return new ItemStack(Material.COBBLE_WALL);
    }
}
