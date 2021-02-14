package dev.feldmann.redstonegang.wire.utils.player;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {

    public static void sendActionBar(Player p, String msg) {
        CraftPlayer craftplayer = (CraftPlayer) p;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        ChatComponentText c = new ChatComponentText(msg);
        PacketPlayOutChat packet = new PacketPlayOutChat(c, (byte) 2);
        connection.sendPacket(packet);
    }

    public static void sendActionBar(String msg) {
        for (Player p: Bukkit.getOnlinePlayers()) {
            sendActionBar(p, msg);
        }
    }

}
