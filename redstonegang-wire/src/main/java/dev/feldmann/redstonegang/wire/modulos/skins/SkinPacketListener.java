package dev.feldmann.redstonegang.wire.modulos.skins;


import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SkinPacketListener extends PacketHandler {

    @Override
    public void onSend(SentPacket sentPacket) {
        if (sentPacket.getPlayer() == null) return;
        if (sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutPlayerInfo")) {
            Enum m = (Enum) sentPacket.getPacketValue("a");
            String action = m.name();
            if (action.equalsIgnoreCase("ADD_PLAYER")) {
                List l = (List) sentPacket.getPacketValue("b");
                for (Object b : l) {
                    try {
                        Field d = b.getClass().getDeclaredField("d");
                        d.setAccessible(true);
                        GameProfile prof = (GameProfile) d.get(b);
                        PlayerSkin data = getSkin(prof.getId());
                        if (data != null) {
                            d.set(b, data.create(sentPacket.getPlayer().getUniqueId() == prof.getId()));
                        }
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutNamedEntitySpawn")) {
            UUID uid = (UUID) sentPacket.getPacketValue("b");
            PlayerSkin data = getSkin(uid);
            if (data != null && data.disguiseName != null) {
                sentPacket.setPacketValue("b", data.getRandUUID());
            }
        }
        if (sentPacket.getPacketName().equalsIgnoreCase("PacketPlayOutScoreboardTeam")) {
            Collection<String> names = (Collection<String>) sentPacket.getPacketValue("g");
            if (names == null || names.isEmpty()) {
                return;
            }
            for (String n : new ArrayList<String>(names)) {
                Player p = Bukkit.getPlayer(n);
                if (p != null && p.isOnline()) {
                    PlayerSkin data = getSkin(p.getUniqueId());
                    if (data != null && data.disguiseName != null) {
                        names.remove(n);
                        names.add(data.disguiseName);
                    }
                }
            }

        }


    }

    private PlayerSkin getSkin(UUID uid) {
        if (!CustomSkin.skins.containsKey(uid)) {
            return null;
        }
        return CustomSkin.skins.get(uid);
    }


    @Override
    public void onReceive(ReceivedPacket receivedPacket) {

    }
}
