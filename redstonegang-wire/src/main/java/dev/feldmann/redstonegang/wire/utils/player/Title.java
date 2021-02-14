package dev.feldmann.redstonegang.wire.utils.player;

import dev.feldmann.redstonegang.wire.utils.NMSUtils;
import com.google.gson.JsonObject;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Title {
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut);
        NMSUtils.sendPacket(player, packetPlayOutTimes);
        if (subtitle != null) {
            subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("text", subtitle);
            IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a(jsonObject.toString());
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            NMSUtils.sendPacket(player, packetPlayOutSubTitle);

        }
        if (title != null) {
            title = title.replaceAll("%player%", player.getDisplayName());
            title = ChatColor.translateAlternateColorCodes('&', title);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("text", title);
            IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a(jsonObject.toString());
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
            NMSUtils.sendPacket(player, packetPlayOutTitle);

        }

    }
}
