package dev.feldmann.redstonegang.wire.utils.location;

import dev.feldmann.redstonegang.wire.utils.hitboxes.Hitbox2D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.BlockFace;

public class LocUtils {


    public static Location loc(double x, double y, double z) {
        return loc(Bukkit.getWorlds().get(0), x, y, z, 0, 0);
    }

    /**
     * @param w Mundo da loc
     */
    public static Location loc(World w, double x, double y, double z, float yaw, float pitch) {
        return new Location(w, x, y, z, yaw, pitch);
    }

    public static boolean isSameBlock(Location l1, Location l2) {
        return (l2.getBlockX() == l1.getBlockX()) && l1.getBlockY() == l2.getBlockY() && l1.getBlockZ() == l2.getBlockZ();
    }

    public static boolean isSameBlockIgnoreY(Location l1, Location l2) {
        return (l2.getBlockX() == l1.getBlockX()) && l1.getBlockZ() == l2.getBlockZ();
    }

    public static BlockFace yawToFace(float yaw) {
        return yawToFace(yaw, false);
    }

    public static BlockFace yawToFace(float yaw, boolean useSubCardinalDirections) {
        if (useSubCardinalDirections)
            return radial[Math.round(yaw / 45f) & 0x7].getOppositeFace();

        return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
    }

    public static Hitbox2D getWorldBorderHitbox(WorldBorder border) {
        Location center = border.getCenter();
        double size = border.getSize() / 2;
        Location min = center.clone().add(-size, 0, -size);
        Location max = center.clone().add(size, 0, size);
        return new Hitbox2D(min.getBlockX(), min.getBlockZ(), max.getBlockX(), max.getBlockZ());
    }

    private static final BlockFace[] axis = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};
    private static final BlockFace[] radial = {BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST};
}
