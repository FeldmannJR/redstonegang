package dev.feldmann.redstonegang.wire.utils.location;

import dev.feldmann.redstonegang.common.RedstoneGang;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.impl.SQLDataType;

import static org.jooq.impl.DSL.*;

public class BungeeLocation {

    private static final Field<String> SERVER = field("loc_server", SQLDataType.VARCHAR(100));
    private static final Field<String> WORLD = field("loc_world", SQLDataType.VARCHAR(100));
    private static final Field<Double> X = field("loc_x", SQLDataType.DOUBLE);
    private static final Field<Double> Y = field("loc_y", SQLDataType.DOUBLE);
    private static final Field<Double> Z = field("loc_z", SQLDataType.DOUBLE);
    private static final Field<Double> YAW = field("loc_yaw", SQLDataType.DOUBLE);
    private static final Field<Double> PITCH = field("loc_pitch", SQLDataType.DOUBLE);

    private static final Field[] fields = new Field[]{SERVER, WORLD, X, Y, Z, YAW, PITCH};

    public static Field[] fields() {
        return fields;
    }


    String server;
    String world;
    double x, y, z;
    float yaw, pitch;

    public BungeeLocation(String server, String world, double x, double y, double z, float yaw, float pitch) {
        this.server = server;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String toString() {
        return server + ";" + world + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
    }

    public Location toLocation() {
        World w = Bukkit.getWorld(this.world);
        if (w != null) {
            return new Location(w, x, y, z, yaw, pitch);
        }
        return null;
    }

    public static BungeeLocation fromLocation(Location loc) {
        return new BungeeLocation(
                RedstoneGang.instance.getNomeServer(),
                loc.getWorld().getName(),
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch());
    }

    public static BungeeLocation fromPlayer(Player p) {
        return fromLocation(p.getLocation());
    }

    public boolean isCurrentServer() {
        return server.equalsIgnoreCase(RedstoneGang.instance.getNomeServer());
    }

    public String getServer() {
        return server;
    }

    public static BungeeLocation fromRecord(Record r) {
        return new BungeeLocation(r.get(SERVER), r.get(WORLD), r.get(X), r.get(Y), r.get(Z), r.get(YAW).floatValue(), r.get(PITCH).floatValue());
    }


    public static BungeeLocation fromString(String str) {
        if (str != null) {
            String[] split = str.split(";");
            if (split.length == 7) {
                String server = split[0];
                String world = split[1];
                try {
                    double x = Double.valueOf(split[2]);
                    double y = Double.valueOf(split[3]);
                    double z = Double.valueOf(split[4]);
                    float yaw = Float.valueOf(split[5]);
                    float pitch = Float.valueOf(split[6]);
                    return new BungeeLocation(server, world, x, y, z, yaw, pitch);
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
        }
        return null;
    }
}
