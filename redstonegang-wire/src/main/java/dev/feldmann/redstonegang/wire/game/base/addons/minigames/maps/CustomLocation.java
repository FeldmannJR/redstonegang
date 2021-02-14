package dev.feldmann.redstonegang.wire.game.base.addons.minigames.maps;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class CustomLocation {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;


    public CustomLocation(double x, double y, double z, float yaw, float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location toLocation(World w) {
        return new Location(w, x, y, z, yaw, pitch);
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }
}
