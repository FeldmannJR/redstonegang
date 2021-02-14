package dev.feldmann.redstonegang.wire.game.base.addons.server.land.flags;

import dev.feldmann.redstonegang.wire.game.base.addons.server.land.LandFlag;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class PvPFlag extends LandFlag {
    @Override
    public String getName() {
        return "PvP";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.DIAMOND_SWORD, 1);
    }

    @Override
    public boolean getDefaultValue() {
        return false;
    }

    @Override
    public String getKey() {
        return "PVP";
    }

    @Override
    public String getDescription() {
        return "Se jogadores podem atacar uns aos outros dentro deste terreno!";
    }

}
