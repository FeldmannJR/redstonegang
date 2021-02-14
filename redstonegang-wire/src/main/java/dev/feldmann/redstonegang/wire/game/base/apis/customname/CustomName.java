package dev.feldmann.redstonegang.wire.game.base.apis.customname;

import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseModule;
import dev.feldmann.redstonegang.wire.modulos.disguise.DisguiseWatcher;
import dev.feldmann.redstonegang.wire.utils.nms.NMS;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CustomName {

    Entity ent;
    String name;
    private boolean spawned = false;

    public CustomName(Entity player, String name) {
        this.ent = player;
        this.name = name;
    }

    public void spawn() {
        if (!spawned) {
            spawned = true;
            for (Player p : getSeeing()) {
                sendSpawn(p);
            }

        }
    }

    public void despawn() {
        if (spawned) {
            spawned = false;
            for (Player p : getSeeing()) {
                destroyArmorStand(p);
            }
        }
    }

    public int getNameId() {
        return Integer.MAX_VALUE - 1000 - (ent.getEntityId() * 2);
    }

    private Object buildArmorStandPacket() {
        DisguiseWatcher watcher = new DisguiseWatcher();
        //marker //baspalte //arms //gravity //small
        byte b = Byte.parseByte("10000", 2);
        watcher.add(10, b);
        watcher.add(2, "");
        watcher.add(3, (byte) 0);
        watcher.add(0, (byte) 0x20);
        return NMS.current.buildSpawnPacket(getNameId(), EntityType.ARMOR_STAND, ent.getLocation(), watcher);
    }

    private Object buildNamePacket() {
        DisguiseWatcher watcher = new DisguiseWatcher();
        //marker //baspalte //arms //gravity //small
        watcher.add(2, name);
        watcher.add(3, (byte) 1);
        return NMS.current.buildMetadata(getNameId(), watcher);

    }

    private Object buildSquid() {
        DisguiseWatcher watcher = new DisguiseWatcher();
        watcher.add(0, (byte) 0x20);
        //   watcher.add(16, (byte) -2);
        return DisguiseModule.nms.buildSpawnPacket(getNameId() + 1, EntityType.SQUID, ent.getLocation(), watcher);
    }

    public void sendSpawn(Player p) {
        DisguiseModule.nms.sendPacket(p, buildArmorStandPacket());
        DisguiseModule.nms.sendPacket(p, buildSquid());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Wire.instance, () -> {
            if (spawned) {
                DisguiseModule.nms.sendPacket(p, DisguiseModule.nms.buildMount(ent.getEntityId(), getNameId() + 1));
                DisguiseModule.nms.sendPacket(p, DisguiseModule.nms.buildMount(getNameId() + 1, getNameId()));
                Bukkit.getScheduler().scheduleSyncDelayedTask(Wire.instance, () -> {
                    if (spawned)
                        DisguiseModule.nms.sendPacket(p, buildNamePacket());
                }, 1);
            }
        });

    }

    public void destroyArmorStand(Player p) {
        DisguiseModule.nms.sendPacket(p, DisguiseModule.nms.buildDestroy(getNameId(), getNameId() + 1));
    }

    public List<Player> getSeeing() {
        List<Player> seeing = new ArrayList();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl != ent && pl.getWorld() == ent.getWorld()) {
                if (DisguiseModule.nms.isPlayerSeeing(pl, ent)) {
                    seeing.add(pl);
                }
            }
        }
        return seeing;

    }
}
