package dev.feldmann.redstonegang.wire.game.base.addons.server.land;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import org.bukkit.inventory.ItemStack;

public abstract class LandFlag {


    public abstract String getName();

    public abstract ItemStack getIcon();

    public abstract boolean getDefaultValue();

    public abstract String getKey();

    public abstract String getDescription();

    public boolean usePermissions() {
        return false;
    }

}
