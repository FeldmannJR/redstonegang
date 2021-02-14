package dev.feldmann.redstonegang.wire.modulos.holos;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author Janhektor
 * @version 1.4 (December 30, 2015)
 */
public class HoloAPI {
    private final List<Object> destroyCache;
    private final List<Object> spawnCache;
    private final List<UUID> players;
    private final List<String> lines;
    private final Location loc;
    private static final double ABS = 0.23D;
    private static String path;
    private static String version;
    /*
     * Cache for getPacket()-Method
     */
    private static Class<?> armorStand;
    private static Class<?> worldClass;
    private static Class<?> nmsEntity;
    private static Class<?> craftWorld;
    private static Class<?> packetClass;
    private static Class<?> entityLivingClass;
    private static Constructor<?> armorStandConstructor;

    /*
     * Cache for getDestroyPacket()-Method
     */
    private static Class<?> destroyPacketClass;
    private static Constructor<?> destroyPacketConstructor;
    /*
     * Cache for sendPacket()-Method
     */
    private static Class<?> nmsPacket;

    static {
        path = Bukkit.getServer().getClass().getPackage().getName();
        version = path.substring(path.lastIndexOf(".") + 1);

        try {
            armorStand = Class.forName("net.minecraft.server." + version + ".EntityArmorStand");
            worldClass = Class.forName("net.minecraft.server." + version + ".World");
            nmsEntity = Class.forName("net.minecraft.server." + version + ".Entity");
            craftWorld = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
            packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutSpawnEntityLiving");
            entityLivingClass = Class.forName("net.minecraft.server." + version + ".EntityLiving");
            armorStandConstructor = armorStand.getConstructor(worldClass);

            destroyPacketClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutEntityDestroy");
            destroyPacketConstructor = destroyPacketClass.getConstructor(int[].class);

            nmsPacket = Class.forName("net.minecraft.server." + version + ".Packet");
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
            System.err.println("Error - Classes not initialized!");
            ex.printStackTrace();
        }
    }

    public HoloAPI(Location loc, String... lines) {
        this(loc, Arrays.asList(lines));
    }

    public HoloAPI(Location loc, List<String> lines) {
        this.lines = lines;
        this.loc = loc;
        this.players = new ArrayList<>();
        this.spawnCache = new ArrayList<>();
        this.destroyCache = new ArrayList<>();

        // Init
        Location displayLoc = loc.clone().add(0, (ABS * lines.size()) - 1.97D, 0);
        for (int i = 0; i < lines.size(); i++) {
            Object packet = this.getPacket(this.loc.getWorld(), displayLoc.getX(), displayLoc.getY(), displayLoc.getZ(), this.lines.get(i));
            this.spawnCache.add(packet);
            try {
                Field field = packetClass.getDeclaredField("a");
                field.setAccessible(true);
                this.destroyCache.add(this.getDestroyPacket((int) field.get(packet)));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            displayLoc.add(0, ABS * (-1), 0);
        }
    }


    public boolean display(Player p) {
        for (int i = 0; i < this.spawnCache.size(); i++) {
            this.sendPacket(p, this.spawnCache.get(i));
        }

        this.players.add(p.getUniqueId());
        return true;
    }

    public boolean destroy(Player p) {
        if (this.players.contains(p.getUniqueId())) {
            for (int i = 0; i < this.destroyCache.size(); i++) {
                this.sendPacket(p, this.destroyCache.get(i));
            }
            this.players.remove(p.getUniqueId());
            return true;
        }
        return false;
    }

    private Object getPacket(World w, double x, double y, double z, String text) {
        try {
            Object craftWorldObj = craftWorld.cast(w);
            Method getHandleMethod = craftWorldObj.getClass().getMethod("getHandle");
            Object entityObject = armorStandConstructor.newInstance(getHandleMethod.invoke(craftWorldObj));
            Method setCustomName = entityObject.getClass().getMethod("setCustomName", String.class);
            setCustomName.invoke(entityObject, text);
            Method setCustomNameVisible = nmsEntity.getMethod("setCustomNameVisible", boolean.class);
            setCustomNameVisible.invoke(entityObject, true);
            Method setGravity = entityObject.getClass().getMethod("setGravity", boolean.class);
            setGravity.invoke(entityObject, false);
            Method setLocation = entityObject.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
            setLocation.invoke(entityObject, x, y, z, 0.0F, 0.0F);
            Method setInvisible = entityObject.getClass().getMethod("setInvisible", boolean.class);
            setInvisible.invoke(entityObject, true);
            Constructor<?> cw = packetClass.getConstructor(entityLivingClass);
            Object packetObject = cw.newInstance(entityObject);
            return packetObject;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object getDestroyPacket(int... id) {
        try {
            return destroyPacketConstructor.newInstance(id);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendPacket(Player p, Object packet) {
        try {
            Method getHandle = p.getClass().getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(p);
            Object pConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            Method sendMethod = pConnection.getClass().getMethod("sendPacket", nmsPacket);
            sendMethod.invoke(pConnection, packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}