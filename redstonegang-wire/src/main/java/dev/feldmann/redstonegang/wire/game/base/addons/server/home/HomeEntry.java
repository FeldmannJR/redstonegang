package dev.feldmann.redstonegang.wire.game.base.addons.server.home;

import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;

public class HomeEntry {

    private int owner;
    private String name;
    private BungeeLocation loc;
    private HomeType type;

    public HomeEntry(int owner, String name, BungeeLocation loc, HomeType type) {
        this.owner = owner;
        this.name = name;
        this.loc = loc;
        this.type = type;
    }

    public int getOwner() {
        return owner;
    }

    public BungeeLocation getLocation() {
        return loc;
    }

    public HomeType getType() {
        return type;
    }

    public void setType(HomeType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }
}
