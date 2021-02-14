package dev.feldmann.redstonegang.wire.utils;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashSet;

public class NMSUtils {

    public static void sendPacket(Player p, Object packet) {
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket((Packet) packet);
    }

    public static void setArrows(Player p, int arrows) {
        ((CraftPlayer) p).getHandle().o(arrows);
    }

    public static void removeInvalidItem(int id) {
        try {
            Field field = ReflectionUtils.getField(ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection"), true, "invalidItems");
            HashSet<Integer> invalid = (HashSet<Integer>) field.get(null);
            invalid.remove(id);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
