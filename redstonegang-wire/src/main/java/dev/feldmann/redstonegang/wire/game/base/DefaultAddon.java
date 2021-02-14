package dev.feldmann.redstonegang.wire.game.base;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;

/**
 * GAMBIARRA, pois só programei pra adicionar option/permissions em addons e não em server :(
 */
public class DefaultAddon extends Addon {
    public PermissionDescription STAFF;

    @Override
    public void onEnable() {
        STAFF = new PermissionDescription("Staff", User.STAFF_PERMISSION, "Se o jogador é membro da staff", true);
        addOption(STAFF);
    }
}
