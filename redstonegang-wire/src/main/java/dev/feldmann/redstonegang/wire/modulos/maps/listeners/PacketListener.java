package dev.feldmann.redstonegang.wire.modulos.maps.listeners;


import dev.feldmann.redstonegang.wire.modulos.maps.ItemFrameManager;
import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import dev.feldmann.redstonegang.wire.modulos.maps.QuadrosManager;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.entity.ItemFrame;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import org.inventivetalent.packetlistener.reflection.resolver.FieldResolver;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.NMSClassResolver;

import java.util.ArrayList;
import java.util.List;

public class PacketListener extends PacketHandler {


    private static FieldResolver watchItem;
    public static NMSClassResolver nmsresolver = new NMSClassResolver();

    public PacketListener() {
        try {
            watchItem = new FieldResolver(nmsresolver.resolve("DataWatcher$WatchableObject"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSend(SentPacket packet) {

        if (packet.getPacketName().equalsIgnoreCase("PacketPlayOutMap")) {
            if (packet.getPlayer() == null) {
                return;
            }
            int id = (int) packet.getPacketValue("a");
            if (id < 0) {
                packet.setPacketValue("a", -id);
            }
            if (packet.getPacketValue("h") == null) {
                packet.setCancelled(true);
            }

        }

        if (packet.getPacketName().equalsIgnoreCase("PacketPlayOutEntityMetadata")) {
            handlePlayOutEntity(packet);
        }
        if (packet.getPacketName().equalsIgnoreCase("PacketPlayOutSpawnEntityLiving")) {
            handleSpawn(packet);
        }


    }

    @Override
    public void onReceive(ReceivedPacket packet) {
        if (packet.getPacketName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            handlePlayInUseEntity(packet);
        }

    }


    public void handlePlayInUseEntity(ReceivedPacket packet) {


    }

    public void handleSpawn(SentPacket packet) {
        int entityid = (int) packet.getPacketValue("a");
        QuadrosManager.isItemFrame(entityid);

        Quadro.QuadroSlot id = QuadrosManager.getFrame(entityid);
        if (id != null) {
            alter(id.itemframe, id.getMapId(packet.getPlayer()), packet, "m");
        }
    }


    public List<DataWatcher.WatchableObject> alter(ItemFrame frame, int mapid, SentPacket packet, String field) {
        int entityid = (int) packet.getPacketValue("a");
        List<DataWatcher.WatchableObject> l = (List<DataWatcher.WatchableObject>) packet.getPacketValue(field);
        ItemStack item = ItemFrameManager.createMap(frame, mapid);
       if (l == null) {
            l = new ArrayList<>();
        } else {
            for (DataWatcher.WatchableObject i : l) {
                if (i.c() == 5) {
                    i.a(item);
                    return null;
                }
            }
        }
        l.add(new DataWatcher.WatchableObject(5, entityid, item));
        packet.setPacketValue(field, l);
        return null;

    }

    public void handlePlayOutEntity(SentPacket packet) {
        int entityid = (int) packet.getPacketValue("a");
        QuadrosManager.isItemFrame(entityid);

        if (entityid < 0) {
            packet.setPacketValue("a", -entityid);
        } else {
            if (packet.getPlayer() != null) {
                Quadro.QuadroSlot id = QuadrosManager.getFrame(entityid);
                if (id != null) {
                    alter(id.itemframe, id.getMapId(packet.getPlayer()), packet, "b");
                }
            }


        }
    }


}
