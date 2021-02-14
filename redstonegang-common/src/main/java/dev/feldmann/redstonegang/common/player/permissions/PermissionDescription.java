package dev.feldmann.redstonegang.common.player.permissions;

public class PermissionDescription extends GroupOption {


    boolean admin = false;

    public PermissionDescription(String nome, String permission, String desc) {
        this(nome, permission, desc, false);
    }

    public PermissionDescription(String nome, String permission, String desc, boolean admin) {
        super(nome, permission, desc);
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }
}
