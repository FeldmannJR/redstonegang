package dev.feldmann.redstonegang.common.player.permissions;

import dev.feldmann.redstonegang.common.RedstoneGang;

import java.util.*;

public class PermissionManager {

    private PermissionsDB db;
    public static PermissionServer defaultServer = PermissionServer.GERAL;
    public static final boolean PLAYER_TYPE = true;

    private HashMap<Integer, Group> groups;


    public PermissionManager() {
        db = RedstoneGang.instance().databases().permissions();
        reloadGroups();
    }

    public void init() {
        checkForDefault();
    }

    protected void reloadGroups() {
        groups = db.loadGroups();
    }

    private void checkForDefault() {
        boolean achou = false;
        for (Group g : groups.values()) {
            if (g.isDefaultGroup()) {
                achou = true;
            }
        }
        if (!achou) {
            db.createGroup("default", "", "", true);
        }

    }

    public PermissionsDB getDb() {
        return db;
    }

    public Group getGroupById(int id) {
        return groups.get(id);
    }

    public Group getGroupByName(String name) {
        for (Group gr : groups.values()) {
            if (gr.getNome().equals(name)) {
                return gr;
            }
        }
        return null;
    }

    public Collection<Group> getGroups() {
        return groups.values();
    }

    public Group getDefault() {
        for (Group g : groups.values()) {
            if (g.isDefaultGroup()) {
                return g;
            }
        }
        return null;
    }


}
