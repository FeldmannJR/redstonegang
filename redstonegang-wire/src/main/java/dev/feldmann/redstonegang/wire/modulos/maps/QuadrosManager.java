package dev.feldmann.redstonegang.wire.modulos.maps;


import dev.feldmann.redstonegang.wire.base.cmds.RedstoneCmd;
import dev.feldmann.redstonegang.wire.base.cmds.arguments.Arguments;
import dev.feldmann.redstonegang.wire.base.modulos.Modulo;
import dev.feldmann.redstonegang.wire.modulos.maps.listeners.PacketListener;
import dev.feldmann.redstonegang.wire.modulos.maps.listeners.QuadrosListener;
import dev.feldmann.redstonegang.wire.modulos.maps.queue.SendMapsQueue;
import dev.feldmann.redstonegang.wire.utils.items.ItemBuilder;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.inventivetalent.packetlistener.PacketListenerAPI;

import java.util.*;


public class QuadrosManager extends Modulo {


    public static SendMapsQueue queue;
    private static HashMap<UUID, QuadroPlayerData> playerData = new HashMap<>();
    public static List<Quadro> quadroList = Collections.synchronizedList(new ArrayList<>());

    public static final double DISTANCE_TO_SEND = 32;

    public void onEnable() {
        queue = new SendMapsQueue();
        register(new QuadrosListener());
        PacketListenerAPI.addPacketHandler(new PacketListener());
        Quadro.maxMapId = (short) (5000 + new Random().nextInt(15000));
        register(new RedstoneCmd("maps") {
            @Override
            public void command(CommandSender sender, Arguments args) {
                for (Quadro c : quadroList) {
                    c.quadros.forEach((k, v) -> {
                        v.clear((Player) sender);
                    });
                }
            }
        });
    }

    @Override
    public void onDisable() {

    }


    //Slot começa em um
    public static int getPos(int tamanho, int max, int maxtamanho, int slot) {
        int resto = maxtamanho - (tamanho * max);
        int entre = resto / (max + 2);

        return (entre * (slot)) + ((slot - 1) * tamanho);


    }


    public static Integer checkQuadro(int entityid, Player p) {
        for (Quadro q : quadroList) {
            for (Quadro.QuadroSlot qs : q.getSlots()) {
                if (qs.itemframe.getEntityId() == entityid) {
                    return qs.getMapId(p);
                }
            }

        }
        return null;
    }

    public static void isItemFrame(int entityId) {
        List<Integer> ids = new ArrayList<>();
        quadroList.forEach(quadro -> {
            quadro.quadros.values().forEach(slot -> {
                ids.add(slot.itemframe.getEntityId());
            });
        });



    }

    public static Quadro.QuadroSlot getFrame(int entityId) {
        Entity w = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().a(entityId);
        for (Quadro q : quadroList) {
            for (Quadro.QuadroSlot qs : q.getSlots()) {
                if (qs.itemframe.getEntityId() == entityId)
                    return qs;
                if (w != null && w.getUniqueID().equals(qs.itemframe.getUniqueId())) {
                    return qs;
                }
            }
        }
        return null;
    }


    private static ItemFrame spawnaFrame(Location b, int x, int y, BlockFace facing) {

        Vector toadd = new Vector(0, 0, 0);
        if (facing != null) {
            if (facing == BlockFace.NORTH || facing == BlockFace.SOUTH) {
                toadd = new Vector(x, -y, 0);
            } else {
                toadd = new Vector(0, -y, x);
            }
        }
        Location t = b.clone().add(toadd);
        if (!t.getChunk().isLoaded()) {
            t.getChunk().load();
        }
        for (ItemFrame m : b.getWorld().getEntitiesByClass(ItemFrame.class)) {
            if (m.getLocation().getBlockX() == t.getBlockX()) {
                if (m.getLocation().getBlockY() == t.getBlockY()) {
                    if (m.getLocation().getBlockZ() == t.getBlockZ()) {
                        return m;
                    }
                }

            }
        }
        return null;
    }

    //O bloco b é o da minimo da direção que tu quer em cima em cima!
    public static boolean setQuadro(Location b, Quadro c) {
        ItemFrame itembase = spawnaFrame(b, 0, 0, null);
        if (itembase == null) {
            return false;
        }
        BlockFace facing = itembase.getFacing();
        c.facing = facing;
        c.createBoundingBox(b.clone());
        for (int x = 0; x < c.getQuadrosX(); x++) {
            for (int y = 0; y < c.getQuadrosY(); y++) {

                int castax = x;
                if (facing == BlockFace.NORTH || facing == BlockFace.EAST) {
                    castax = c.getQuadrosX() - x - 1;
                }
                ItemFrame spawna = spawnaFrame(b, castax, y, facing);
                if (spawna == null) {
                    return false;
                }
                spawna.setRotation(Rotation.NONE);
                spawna.setItem(new ItemStack(Material.MAP));
                facing = spawna.getFacing();
                c.getSlot(x, y).setItemFrame(spawna);
            }
        }
        quadroList.add(c);
        return true;
    }


    public static boolean isMapItemFrame(ItemFrame frame) {
        for (Quadro c : quadroList) {
            for (Quadro.QuadroSlot qs : c.getSlots()) {
                if (qs.itemframe == frame) {
                    return true;
                }
            }

        }
        return false;

    }

    public static QuadroPlayerData getData(Player p) {
        if (!playerData.containsKey(p.getUniqueId())) {
            playerData.put(p.getUniqueId(), new QuadroPlayerData());
        }
        return playerData.get(p.getUniqueId());
    }

    public static void clearPlayer(Player p) {
        for (Quadro c : quadroList) {
            c.lastSent.remove(p.getUniqueId());
            for (Quadro.QuadroSlot qs : c.getSlots()) {
                qs.mapids.remove(p);
            }
            c.sending.remove(p.getUniqueId());
        }
        queue.clear(p.getUniqueId());
        playerData.remove(p.getUniqueId());
    }


    public static ItemStack geraItemStack(int mapid) {
        return ItemBuilder.item(Material.SPONGE).name("map:" + mapid).build();

    }

    public static int getMapid(ItemStack it) {
        if (it == null) {
            return -1;
        }
        if (it != null && it.hasItemMeta() && it.getItemMeta().hasDisplayName()) {
            if (it.getType() == Material.SPONGE) {
                String nome = ChatColor.stripColor(it.getItemMeta().getDisplayName());
                if (nome.startsWith("map:")) {
                    String[] split = nome.split(":");

                    int id = Integer.valueOf(split[1]);

                    return id;
                }
            }
        }
        return -1;
    }


    protected static Quadro getQuadroById(int id) {
        for (Quadro q : quadroList) {
            if (q.id == id) {
                return q;
            }
        }
        return null;
    }


}


