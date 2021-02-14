package dev.feldmann.redstonegang.wire.modulos.disguise.listeners;

import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseAPI;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseModule;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.DisguiseData;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.EquipmentData;
import dev.feldmann.redstonegang.wire.modulos.disguise.types.base.LivingData;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import java.util.UUID;

public class PacketListener extends PacketHandler {

    public void onSend(SentPacket sentPacket) {

        if (sentPacket.getPlayer() == null) {
            return;
        }
        if (sentPacket.getPacketName().equals("PacketPlayOutNamedEntitySpawn")) {
            handleSpawn(sentPacket);
        }
        if (sentPacket.getPacketName().equals("PacketPlayOutEntityEquipment")) {
            handleEquipment(sentPacket);
        }
        if (sentPacket.getPacketName().equals("PacketPlayOutEntityDestroy")) {
            handleDestroi(sentPacket);
        }

    }

    public void onReceive(ReceivedPacket receivedPacket) {

    }

    public void handleEquipment(SentPacket pa) {
        int id = (Integer) pa.getPacketValue("a");
        if (id < 0) {
            pa.setPacketValue("a", -id);
            return;
        }
        Player e = DisguiseModule.getEntityById(id);
        if (e == null) {
            return;
        }
        DisguiseData data = DisguiseAPI.getDisguise(e.getUniqueId());
        if (data != null) {
            if (data instanceof EquipmentData) {
                if (!((EquipmentData) data).isShowingPlayerEquipment()) {
                    pa.setCancelled(true);
                }
            } else if (data instanceof LivingData) {
                pa.setCancelled(true);
            }
        }

    }

    public void handleDestroi(SentPacket pa) {

        for (int id: (int[]) pa.getPacketValue("a")) {
            Player p = DisguiseModule.getEntityById(id);
            if (p == null) return;
            DisguiseData data = DisguiseAPI.getDisguise(p.getUniqueId());
            if (data != null && data instanceof LivingData) {
                ((LivingData) data).destroyArmorStand(pa.getPlayer());
            }

        }
    }

    public void handleSpawn(SentPacket pa) {

        UUID uid = (UUID) pa.getPacketValue("b");
        DisguiseData data = DisguiseAPI.getDisguise(uid);
        if (data != null && data instanceof LivingData) {
            pa.setCancelled(true);
            ((LivingData) data).sendSpawn(pa.getPlayer());
        }

    }

}
