package dev.feldmann.redstonegang.wire.game.base.apis.customname;

import dev.feldmann.redstonegang.wire.game.base.objects.API;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.inventivetalent.packetlistener.PacketListenerAPI;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AboveNameAPI extends API {


    private ConcurrentHashMap<UUID, CustomName> players = new ConcurrentHashMap<UUID,CustomName>();
    private PacketListener listener;

    @Override
    public void onEnable() {
        this.listener = new PacketListener(this);
        PacketListenerAPI.addPacketHandler(listener);
    }


    @Override
    public void onDisable() {
        PacketListenerAPI.removePacketHandler(listener);

    }

    public ConcurrentHashMap<UUID, CustomName> getPlayers() {
        return players;
    }

    @EventHandler
    public void quit(PlayerQuitEvent ev) {
        CustomName na = players.remove(ev.getPlayer().getUniqueId());
        if (na != null)
            na.despawn();
    }

    @EventHandler
    public void death(PlayerDeathEvent ev) {
        if (players.containsKey(ev.getEntity().getUniqueId())) {
            CustomName customName = players.remove(ev.getEntity().getUniqueId());
            customName.despawn();
        }
    }

    @EventHandler
    public void sneak(PlayerToggleSneakEvent ev) {
        if (players.containsKey(ev.getPlayer().getUniqueId())) {
            CustomName na = players.get(ev.getPlayer().getUniqueId());
            if (ev.isSneaking()) {
                na.despawn();
            } else {
                na.spawn();
            }
        }
    }

    public void setaNome(Entity p, String nome) {
        if (players.containsKey(p.getUniqueId())) {
            players.get(p.getUniqueId()).despawn();
        }
        if (nome == null) {
            players.remove(p.getUniqueId());
            return;
        }
        CustomName name = new CustomName(p, nome);
        players.put(p.getUniqueId(), name);
        name.spawn();
    }

    public CustomName get(UUID uuid) {
        if (players.containsKey(uuid)) {
            return players.get(uuid);
        }
        return null;
    }


}
