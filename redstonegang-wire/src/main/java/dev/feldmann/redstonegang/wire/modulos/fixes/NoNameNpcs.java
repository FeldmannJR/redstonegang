package dev.feldmann.redstonegang.wire.modulos.fixes;

import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.utils.NMSUtils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;
import org.inventivetalent.packetlistener.reflection.resolver.FieldResolver;
import org.inventivetalent.packetlistener.reflection.resolver.minecraft.NMSClassResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class NoNameNpcs extends Modulo implements Listener {


    private static FieldResolver outScoreboardTeam;
    private static NMSClassResolver nmsResolver;

    public static List<UUID> criouTeam = new ArrayList();

    @Override
    public void onEnable() {

        nmsResolver = new NMSClassResolver();
        outScoreboardTeam = new FieldResolver(nmsResolver.resolveSilent("PacketPlayOutScoreboardTeam"));

        PacketListenerAPI.addPacketHandler(new PacketHandler() {
            @Override
            public void onSend(SentPacket packet) {
                if (packet.getPacketName().equalsIgnoreCase("PacketPlayOutScoreboardTeam")) {

                    Collection<String> players = (Collection<String>) packet.getPacketValue("g");
                }
                if (packet.getPacket() instanceof PacketPlayOutNamedEntitySpawn) {
                    if (packet.getPlayer() == null) {
                        return;
                    }
                    String n = isNPC((UUID) packet.getPacketValue("b"));
                    if (n != null) {
                        if (!ChatColor.stripColor(n).equalsIgnoreCase("")) {
                            return;
                        }

                        PacketPlayOutScoreboardTeam pa = new PacketPlayOutScoreboardTeam();
                        try {
                            if (criouTeam.contains(packet.getPlayer().getUniqueId())) {
                                outScoreboardTeam.resolve("h").set(pa, 3);
                            } else {
                                outScoreboardTeam.resolve("h").set(pa, 0);
                                outScoreboardTeam.resolve("e").set(pa, "never");
                                outScoreboardTeam.resolve("i").set(pa, 0);
                            }
                            criouTeam.add(packet.getPlayer().getUniqueId());
                            outScoreboardTeam.resolve("a").set(pa, "npchide");
                            ((Collection) outScoreboardTeam.resolve("g").get(pa)).add(n);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        NMSUtils.sendPacket(packet.getPlayer(), pa);


                    }
                }

            }

            @Override
            public void onReceive(ReceivedPacket packet) {

            }
        });


    }

    public static String isNPC(UUID uid) {
        NPCRegistry re = CitizensAPI.getNPCRegistry();
        NPC n = re.getByUniqueId(uid);
        if (n != null) {
            return n.getName();
        }
        return null;
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        criouTeam.remove(ev.getPlayer().getUniqueId());
    }

    @Override
    public void onDisable() {

    }
}
