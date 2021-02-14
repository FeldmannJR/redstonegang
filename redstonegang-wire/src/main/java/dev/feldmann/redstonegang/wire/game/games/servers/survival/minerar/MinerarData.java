package dev.feldmann.redstonegang.wire.game.games.servers.survival.minerar;

import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox;
import org.bukkit.Location;

public class MinerarData {


    private int pId;
    private Location loc;
    private Location lastLoc;
    private Hitbox protectedArea = null;
    private Hitbox anotherProtectedArea = null;

    public MinerarData(int pId) {
        this.pId = pId;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public Hitbox getProtectedArea() {
        return protectedArea;
    }

    public void setProtectedArea(Hitbox protectedArea) {
        this.protectedArea = protectedArea;
    }

    public Hitbox getAnotherProtectedArea() {
        return anotherProtectedArea;
    }

    public void setAnotherProtectedArea(Hitbox anotherProtectedArea) {
        this.anotherProtectedArea = anotherProtectedArea;
    }

    public Location getLocation() {
        return loc;
    }

    public void setLastLoc(Location lastLoc) {
        this.lastLoc = lastLoc;
    }

    public Location getLastLoc() {
        return lastLoc;
    }
}
