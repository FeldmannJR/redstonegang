package dev.feldmann.redstonegang.common.player.permissions;

import dev.feldmann.redstonegang.common.RedstoneGang;

import java.util.HashMap;

import static dev.feldmann.redstonegang.common.player.permissions.PermissionManager.defaultServer;

public abstract class PermissionHolder {
    //Id do grupo que é parent
    private Integer parentId;
    //Permissões
    private HashMap<PermissionServer, HashMap<String, PermissionValue>> permissionList = new HashMap<>();


    public PermissionHolder(Integer parent) {
        this.parentId = parent;
    }

    public boolean hasPermission(String permission) {
        PermissionValue value = getPermission(defaultServer, permission);
        if (value != PermissionValue.NONE) {
            return value == PermissionValue.ALLOW;
        }
        value = getPermission(PermissionServer.GERAL, permission);
        return value == PermissionValue.ALLOW;
    }

    public boolean isPermissionSet(String permission) {
        PermissionValue value = getPermission(defaultServer, permission);
        if (value != PermissionValue.NONE) {
            return true;
        }
        value = getPermission(PermissionServer.GERAL, permission);
        return value != PermissionValue.NONE;
    }

    public HashMap<PermissionServer, HashMap<String, PermissionValue>> getPermissionList() {
        return permissionList;
    }

    public PermissionValue getPermission(PermissionServer server, String perm) {
        HashMap<String, PermissionValue> permissions = getServer(server);
        if (permissions.containsKey("*")) {
            if (permissions.get("*").equals(PermissionValue.ALLOW)) {
                return PermissionValue.ALLOW;
            }
        }
        if (permissions.containsKey(perm)) {
            PermissionValue va = permissions.get(perm);
            if (va != PermissionValue.NONE) {
                return va;
            }
        }

        Group gparent = getParent();
        if (gparent != null) {
            return gparent.getPermission(server, perm);
        }

        return PermissionValue.NONE;
    }



    public Group getParent() {
        if (parentId != null) {
            Group gparent = RedstoneGang.instance.user().getPermissions().getGroupById(this.parentId);
            if (gparent != null) {
                return gparent;
            }
        }
        if (useDefaultGroup()) {
            return RedstoneGang.instance.user().getPermissions().getDefault();
        }
        return null;
    }

    public boolean useDefaultGroup() {
        return false;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setPermissionList(HashMap<PermissionServer, HashMap<String, PermissionValue>> permissionList) {
        this.permissionList = permissionList;
    }


    public HashMap<String, PermissionValue> getServer(PermissionServer server) {
        if (!permissionList.containsKey(server)) {
            permissionList.put(server, new HashMap<>());
        }
        return permissionList.get(server);

    }

    /*
     * Não insere no banco
     * */
    protected void setPermission(PermissionServer server, String key, PermissionValue value) {
        HashMap<String, PermissionValue> permission = getServer(server);
        if (value == PermissionValue.NONE) {
            permission.remove(key);
            return;
        }
        permission.put(key, value);
    }

    /**
     * Insere no banco
     */
    public void addPermission(PermissionServer server, String key, PermissionValue value) {
        RedstoneGang.instance.user().getPermissions().getDb().setPermission(this, server, key, value);
        setPermission(server, key, value);
    }

    public abstract Integer getIdentifier();

    public abstract boolean getType();
}
