package dev.feldmann.redstonegang.wire.game.base.addons.server.invsync;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.RedstoneGangSpigot;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events.PlayerSyncLocationEvent;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.PlayerInventoriesRecord;
import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerTeleportToServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.BaseListener;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events.PlayerSaveLocationEvent;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import dev.feldmann.redstonegang.wire.utils.player.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.util.HashMap;

public class SyncListener extends BaseListener {

    private HashMap<Integer, Integer> teleportTasks = new HashMap<>();

    InvSync sync;

    public SyncListener(InvSync sync) {
        this.sync = sync;
    }

    @EventHandler
    public void join(PlayerJoinEvent ev) {
        PlayerInventoriesRecord syncing = sync.loadingSync.get(sync.getPlayerId(ev.getPlayer()));
        PlayerUtils.limpa(ev.getPlayer());
        if (syncing != null) {
            PlayerInventoriesRecord inv = sync.loadingSync.get(sync.getPlayerId(ev.getPlayer()));
            sync.loadPlayer(ev.getPlayer(), inv);
            sync.loadingSync.remove(sync.getPlayerId(ev.getPlayer()));
        } else {
            ev.getPlayer().kickPlayer("§cOcorreu um erro #2!");
        }
    }


    /**
     * Este evento é chamado antes do join, assim eu posso setar o local do jogador onde ele vai spawnar, não precisando teleportar
     * assim o nego n precisa carregar 2 vezes as chunks
     * o problema que agora fica + complexo pois não consigo setar os itens do inventário aqui
     * Então estou quebrando o isto em dois eventos
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void spawn(PlayerSpawnLocationEvent ev) {
        try {
            sync.loadingSync.put(sync.getPlayerId(ev.getPlayer()), null);
            int id = RedstoneGang.getPlayer(ev.getPlayer().getUniqueId()).getId();
            PlayerInventoriesRecord inv = sync.db.loadInv(RedstoneGang.getPlayer(ev.getPlayer().getUniqueId()).getId());
            if (inv == null) {
                inv = sync.db.savePlayer(ev.getPlayer(), id, BungeeLocation.fromLocation(ev.getPlayer().getWorld().getSpawnLocation()), null, false);
            }
            sync.loadingSync.put(sync.getPlayerId(ev.getPlayer()), inv);
            PlayerSyncLocationEvent syncEvent = new PlayerSyncLocationEvent(ev.getPlayer(), BungeeLocation.fromString(inv.getLocation()), BungeeLocation.fromString(inv.getTeleportLocation()));
            Bukkit.getPluginManager().callEvent(syncEvent);
            Location spawn = syncEvent.getSpawnLocation();
            if (spawn == null) {
                spawn = ev.getPlayer().getWorld().getSpawnLocation();
            }
            ev.setSpawnLocation(spawn);
        } catch (Exception ex) {
            ex.printStackTrace();
            ev.getPlayer().kickPlayer("Ocorreu um erro!");
        }
    }

    @EventHandler
    public void teleport(PlayerTeleportToServerEvent ev) {
        final int playerId = sync.getPlayerId(ev.getPlayer());
        if (teleportTasks.containsKey(playerId)) {
            ev.setCancelled(true);
            return;
        }

        PlayerSaveLocationEvent saveLoc = new PlayerSaveLocationEvent(ev.getPlayer(), BungeeLocation.fromLocation(ev.getPlayer().getLocation()), true);
        Wire.callEvent(saveLoc);
        sync.save(ev.getPlayer(), saveLoc.getSaveLocation(), ev.getLoc());

        PlayerUtils.limpa(ev.getPlayer());
        ev.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 10, 3));
        int task = sync.scheduler().runAfter(() -> {
            teleportTasks.remove(playerId);
            kickPlayerId(playerId);
        }, 20 * 10);
        teleportTasks.put(playerId, task);
    }

    public void kickPlayerId(int playeId) {
        Player player = RedstoneGangSpigot.getOnlinePlayer(playeId);
        if (player != null) {
            player.kickPlayer("§cOcorreu um erro!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void quit(PlayerQuitEvent ev) {
        int pid = sync.getPlayerId(ev.getPlayer());
        if (teleportTasks.containsKey(pid)) {
            Integer task = teleportTasks.get(pid);
            sync.scheduler().cancelTask(task);
            teleportTasks.remove(pid);
        }


        sync.save(ev.getPlayer());
    }


    public boolean loading(Player p) {
        return sync.loadingSync.containsKey(sync.getPlayerId(p));
    }

    @EventHandler
    public void move(PlayerMoveEvent ev) {
        if (loading(ev.getPlayer())) {
            ev.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void interact(PlayerInteractEvent ev) {
        if (loading(ev.getPlayer())) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void drop(PlayerDropItemEvent ev) {
        if (loading(ev.getPlayer())) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void place(BlockPlaceEvent ev) {
        if (loading(ev.getPlayer())) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent ev) {
        if (loading(ev.getPlayer())) {
            ev.setCancelled(true);
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent ev) {
        if (loading(ev.getPlayer())) {
            ev.setCancelled(true);
        }
    }

}
