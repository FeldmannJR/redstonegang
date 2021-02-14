package dev.feldmann.redstonegang.wire.modulos.maps.queue;

import dev.feldmann.redstonegang.wire.modulos.maps.Quadro;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutMap;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MapQueueEntry extends QueueEntry {

    public Packet packet;

    public MapQueueEntry(Player p, Packet pacote) {
        super(p);
        this.packet = pacote;
    }

    public MapQueueEntry(Player p, Quadro q, int mapid, int offsetX, int offsetY, int xModificados, int yModificados, byte[] data) {
        super(p);
        packet = new PacketPlayOutMap(mapid, (byte) 0, new ArrayList<>(), data, offsetX, offsetY, xModificados, yModificados);
    }


}
