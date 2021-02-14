package dev.feldmann.redstonegang.wire.modulos.maps;


import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItemFrame;
import org.bukkit.entity.ItemFrame;
import org.inventivetalent.packetlistener.reflection.resolver.FieldResolver;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.NMSClassResolver;

public class ItemFrameManager {


    private static FieldResolver entityMetadata = null;
    private static NMSClassResolver nmsresolver = new NMSClassResolver();

    public static Packet setItemFrameMap(ItemFrame frame, short mapid) {
        EntityItemFrame nmsframe = ((CraftItemFrame) frame).getHandle();
        try {
            if (entityMetadata == null) {

                entityMetadata = new FieldResolver(nmsresolver.resolve("PacketPlayOutEntityMetadata"));


            }

            ItemStack item = new net.minecraft.server.v1_8_R3.ItemStack(Item.d("filled_map"), 1, mapid);
            item.a(nmsframe);
            DataWatcher watcher = new DataWatcher(nmsframe);
            watcher.a(8, item);
            watcher.a(3, (byte) 0);
            watcher.update(8);

            PacketPlayOutEntityMetadata pacote = new PacketPlayOutEntityMetadata(-nmsframe.getId(), watcher, false);
            return pacote;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack createMap(ItemFrame frame, int mapid) {
        EntityItemFrame nmsframe = ((CraftItemFrame) frame).getHandle();
        ItemStack item = new net.minecraft.server.v1_8_R3.ItemStack(Item.d("filled_map"), 1, mapid);
        item.a(nmsframe);
        return item;
    }

    public static Packet clear(ItemFrame frame) {
        EntityItemFrame nmsframe = ((CraftItemFrame) frame).getHandle();
        try {
            if (entityMetadata == null) {

                entityMetadata = new FieldResolver(nmsresolver.resolve("PacketPlayOutEntityMetadata"));


            }

            ItemStack item = new net.minecraft.server.v1_8_R3.ItemStack(Item.d("stone"), 1);
            item.a(nmsframe);
            DataWatcher watcher = new DataWatcher(nmsframe);
            watcher.a(8, item);
            watcher.a(3, (byte) 0);
            watcher.update(8);

            PacketPlayOutEntityMetadata pacote = new PacketPlayOutEntityMetadata(-nmsframe.getId(), watcher, false);
            return pacote;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return null;

    }
}
