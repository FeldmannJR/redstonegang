package dev.feldmann.redstonegang.wire.game.base.addons.server.land;

import dev.feldmann.redstonegang.common.player.User;
import dev.feldmann.redstonegang.common.player.permissions.PermissionDescription;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.flags.PvPFlag;
import dev.feldmann.redstonegang.wire.game.base.addons.server.land.flags.SpawnMobsFlag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LandFlags {

    public static final PvPFlag PVP = new PvPFlag();
    public static final SpawnMobsFlag SPAWN_MOBS = new SpawnMobsFlag();

    private LandAddon addon;

    public LandFlags(LandAddon addon) {
        this.addon = addon;
    }

    private List<LandFlag> flags = new ArrayList<>();

    public void onEnable() {
        flags.add(PVP);
        flags.add(SPAWN_MOBS);
        for (LandFlag flag : flags) {
            if (flag.usePermissions())
                addon.addOption(buildPermission(flag));
        }
    }


    public boolean canEdit(User user, LandFlag flag) {
        if (flag.usePermissions()) {
            return user.hasPermission(buildPermission(flag));
        }
        return true;
    }

    private PermissionDescription buildPermission(LandFlag flag) {
        return new PermissionDescription("Modificar Flag " + flag.getName(), addon.generatePermission("flag." + flag.getKey().toLowerCase()), "Jogadores com está permissão conseguem modificar a flag " + flag.getName() + " do terreno!");
    }

    public List<LandFlag> all() {
        return Collections.unmodifiableList(flags);
    }
}
