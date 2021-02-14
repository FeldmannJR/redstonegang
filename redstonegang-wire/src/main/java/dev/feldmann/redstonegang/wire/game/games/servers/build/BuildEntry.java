package dev.feldmann.redstonegang.wire.game.games.servers.build;

import dev.feldmann.redstonegang.wire.game.base.objects.ServerEntry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BuildEntry extends ServerEntry {
    public BuildEntry() {
        super(Build.class);
    }

    @Override
    public String getNome() {
        return "Build";
    }

    @Override
    public String getMapsFolder() {
        return null;
    }

    @Override
    public ItemStack getIcone() {
        return new ItemStack(Material.WOOD_AXE);
    }
}
