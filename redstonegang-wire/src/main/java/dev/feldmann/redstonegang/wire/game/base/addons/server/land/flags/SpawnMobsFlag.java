package dev.feldmann.redstonegang.wire.game.base.addons.server.land.flags;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandFlag;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SpawnMobsFlag extends LandFlag {
    @Override
    public String getName() {
        return "Spawn de Mobs";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
    }

    @Override
    public boolean getDefaultValue() {
        return true;
    }

    @Override
    public boolean usePermissions() {
        return true;
    }

    @Override
    public String getKey() {
        return "SPAWN_MOBS";
    }

    @Override
    public String getDescription() {
        return "Se mobs agressivos vão spawnar dentro do terreno!";
    }
}
