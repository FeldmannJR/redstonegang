package dev.feldmann.redstonegang.wire.modulos.disguise;

import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.modulos.disguise.cmds.CmdDisguise;
import dev.feldmann.redstonegang.wire.modulos.disguise.listeners.BukkitListener;
import dev.feldmann.redstonegang.wire.modulos.disguise.listeners.PacketListener;
import dev.feldmann.redstonegang.wire.modulos.disguise.listeners.PlayerPacketListener;
import dev.feldmann.redstonegang.wire.utils.nms.NMS;
import dev.feldmann.redstonegang.wire.utils.nms.NMSVersionImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;

public class DisguiseModule extends Modulo {

    public static NMSVersionImpl nms = null;
    public static String skinUrl = null;

    @Override
    public void onEnable() {
        nms = NMS.current;
        PacketListenerAPI.addPacketHandler(new PlayerPacketListener());
        PacketListenerAPI.addPacketHandler(new PacketListener());
        register(new BukkitListener());
        register(new CmdDisguise());

    }

    @Override
    public void onDisable() {

    }

    public static Player getEntityById(int id) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getEntityId() == id) {
                return p;
            }
        }

        return null;
    }
}
