package dev.feldmann.redstonegang.wire.game.base.apis.customname;


import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseModule;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import java.util.UUID;

public class PacketListener extends PacketHandler {

    AboveNameAPI names;

    public PacketListener(AboveNameAPI names) {
        this.names = names;
    }

    @Override
    public void onSend(SentPacket sentPacket) {
        if (sentPacket.getPlayer() == null) return;
        if (sentPacket.getPacketName().equals("PacketPlayOutNamedEntitySpawn")) {
            handleSpawn(sentPacket);
        }

        if (sentPacket.getPacketName().equals("PacketPlayOutEntityDestroy")) {
            handleDestroi(sentPacket);
        }

    }

    @Override
    public void onReceive(ReceivedPacket receivedPacket) {
        if (receivedPacket.getPacket() instanceof PacketPlayInFlying) {
            PacketPlayInFlying packet = (PacketPlayInFlying) receivedPacket.getPacket();
        }
        if (receivedPacket.getPacket() instanceof PacketPlayInUseEntity) {
            handleDamage(receivedPacket);
        }
    }

    // Transfere o dano que as entidades que são usadas para criar o nome para o jogador
    public void handleDamage(ReceivedPacket ev) {
        int target = (int) ev.getPacketValue(0);
        for (CustomName customname : names.getPlayers().values()) {
            int id = customname.getNameId() + 1;
            if (id == target) {
                ev.setPacketValue(0, customname.ent.getEntityId());
            }
        }
    }

    public void handleDestroi(SentPacket pa) {

        for (int id : (int[]) pa.getPacketValue("a")) {
            Player p = DisguiseModule.getEntityById(id);
            if (p == null) return;
            CustomName data = names.get(p.getUniqueId());
            if (data != null) {
                data.destroyArmorStand(pa.getPlayer());
            }
        }
    }

    public void handleSpawn(SentPacket pa) {
        UUID uid = (UUID) pa.getPacketValue("b");
        CustomName data = names.get(uid);
        if (data != null) {
            data.sendSpawn(pa.getPlayer());
        }
    }


}
