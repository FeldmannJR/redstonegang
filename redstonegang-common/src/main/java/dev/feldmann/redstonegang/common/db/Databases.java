package dev.feldmann.redstonegang.common.db;

import dev.feldmann.redstonegang.common.errors.ExceptionsDB;
import dev.feldmann.redstonegang.common.player.UserDB;
import dev.feldmann.redstonegang.common.player.permissions.PermissionsDB;
import dev.feldmann.redstonegang.common.servers.ServerDB;

public class Databases {


    private ServerDB servers;
    private UserDB users;
    private PermissionsDB permissions;
    private ExceptionsDB exceptions;

    public Databases() {
        servers = new ServerDB();
        users = new UserDB();
        permissions = new PermissionsDB();
        exceptions = new ExceptionsDB();
    }

    public ExceptionsDB exceptions() {
        return exceptions;
    }

    public UserDB users() {
        return users;
    }

    public PermissionsDB permissions() {
        return permissions;
    }

    public ServerDB servers() {
        return servers;
    }


}
