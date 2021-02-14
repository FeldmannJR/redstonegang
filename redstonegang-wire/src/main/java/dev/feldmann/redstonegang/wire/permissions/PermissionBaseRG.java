package dev.feldmann.redstonegang.wire.permissions;

import dev.feldmann.redstonegang.common.RedstoneGang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.Permission;

import java.util.UUID;

public class PermissionBaseRG extends PermissibleBase {
    UUID uid;

    public PermissionBaseRG(Player p) {
        super(p);
        this.uid = p.getUniqueId();
        p.setOp(isOp());
    }

    @Override
    public boolean isOp() {
        return hasInternalPerm("redstonegang.op");
    }


    public static void updateOp(Player p) {
        p.setOp(p.hasPermission("redstonegang.op"));
    }

    @Override
    public boolean hasPermission(String name) {
        if (name == null) {
            return false;
        }
        if (isPermissionSet(name)) {
            return hasInternalPerm(name);
        }
        Permission perm = Bukkit.getServer().getPluginManager().getPermission(name);
        return perm != null ? perm.getDefault().getValue(this.isOp()) : Permission.DEFAULT_PERMISSION.getValue(this.isOp());
    }

    @Override
    public void recalculatePermissions() {
    }


    @Override
    public boolean isPermissionSet(String name) {
        return RedstoneGang.getPlayer(this.uid).permissions().isPermissionSet(name);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        if (perm != null && perm.getName() != null) {
            return hasPermission(perm.getName());
        }
        return false;
    }

    private boolean hasInternalPerm(String perm) {
        return RedstoneGang.getPlayer(this.uid).permissions().hasPermission(perm);
    }
}
