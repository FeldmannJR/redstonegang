package dev.feldmann.redstonegang.wire.utils.location;

import dev.feldmann.redstonegang.common.RedstoneGang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Objects;

public class BungeeBlock {

    String server;
    String world;
    int x, y, z;

    public BungeeBlock(String server, String world, int x, int y, int z) {
        this.server = server;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public String toString() {
        return server + ";" + world + ";" + x + ";" + y + ";" + z;
    }

    public Block toBlock() {
        if (RedstoneGang.instance.getNomeServer().equals(server)) {
            World w = Bukkit.getWorld(this.world);
            if (w != null) {
                return w.getBlockAt(x, y, z);
            }
        }
        return null;
    }

    public static BungeeBlock fromBlock(Block b) {
        return new BungeeBlock(
                RedstoneGang.instance.getNomeServer(),
                b.getWorld().getName(),
                b.getX(),
                b.getY(),
                b.getZ());
    }

    public String getWorld() {
        return world;
    }

    public static BungeeBlock fromString(String str) {
        if (str != null) {
            String[] split = str.split(";");
            if (split.length == 5) {
                String server = split[0];
                String world = split[1];
                try {
                    int x = Integer.valueOf(split[2]);
                    int y = Integer.valueOf(split[3]);
                    int z = Integer.valueOf(split[4]);
                    return new BungeeBlock(server, world, x, y, z);
                } catch (NumberFormatException ex) {
                    return null;
                }
            }
        }
        return null;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public boolean isSameIgnoreY(BungeeBlock b2) {
        return b2.x == x && b2.z == b2.z && b2.world.equals(world) && b2.server.equals(server);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BungeeBlock)) {
            return false;
        }
        BungeeBlock b2 = (BungeeBlock) obj;
        return server.equals(b2.server) && world.equals(b2.world) && b2.x == x && b2.y == y && b2.z == z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, world, x, y, z);
    }
}
