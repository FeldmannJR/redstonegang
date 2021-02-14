package dev.feldmann.redstonegang.wire.game.base.addons.server.invsync;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.wire.Wire;
import dev.feldmann.redstonegang.wire.game.base.addons.server.invsync.events.PlayerSaveLocationEvent;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.PlayerInventoriesRecord;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;
import dev.feldmann.redstonegang.wire.utils.player.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.function.Consumer;

public class InvSync extends Addon {
    InvSyncDB db;
    HashMap<Integer, PlayerInventoriesRecord> loadingSync = new HashMap();

    // Gambiarra Pesada
    private Consumer<PlayerSaveLocationEvent> onDisableHook = null;

    public InvSync(String database) {
        this.db = new InvSyncDB(database);
    }

    @Override
    public void onStart() {
        registerListener(new SyncListener(this));
        RedstoneGang.instance.runRepeatingTask(this::updateAll, 20 * 60 * 5);
    }

    public void setOnDisableHook(Consumer<PlayerSaveLocationEvent> onDisableHook) {
        this.onDisableHook = onDisableHook;
    }

    @Override
    public void onDisable() {
        System.out.println(db != null);
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerSaveLocationEvent evCall = new PlayerSaveLocationEvent(p, BungeeLocation.fromPlayer(p), false);
            //Wire.callEvent(evCall); - Não da pra chamar evento no on disable, tenq fazer gambeta mesmo foda-se
            if (onDisableHook != null) {
                onDisableHook.accept(evCall);
            }
            BungeeLocation save = evCall.getSaveLocation();
            save(p, save, null);
        }
    }


    public void updateAll() {
        RedstoneGang.instance.runAsync(() -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!loadingSync.containsKey(getPlayerId(p))) {
                    PlayerSaveLocationEvent ev = new PlayerSaveLocationEvent(p, BungeeLocation.fromPlayer(p), false);
                    Wire.callEvent(ev);
                    BungeeLocation save = ev.getSaveLocation();
                    db.savePlayer(p, getPlayerId(p), save, null, true);
                }
            }
        });
    }

    public boolean isWaitingLoad(Player p) {
        return loadingSync.containsKey(getPlayerId(p));
    }

    public void save(Player p, BungeeLocation saveLocation, BungeeLocation teleportLocation) {
        if (!loadingSync.containsKey(getPlayerId(p))) {
            int id = RedstoneGang.getPlayer(p.getUniqueId()).getId();
            PlayerInventoriesRecord record = db.savePlayer(p, id, saveLocation, teleportLocation, true);
            loadingSync.put(id, record);
        }
    }

    public void save(Player p) {
        PlayerSaveLocationEvent ev = new PlayerSaveLocationEvent(p, BungeeLocation.fromPlayer(p), false);
        Wire.callEvent(ev);
        BungeeLocation save = ev.getSaveLocation();
        save(p, save, null);
    }

    public void loadPlayer(Player p, PlayerInventoriesRecord r) {
        PlayerUtils.limpa(p);
        p.getInventory().setContents(ItemSerializer.deserializeItemStacks(r.getInvcontents()));
        p.getInventory().setArmorContents(ItemSerializer.deserializeItemStacks(r.getArmorcontents()));
        p.getEnderChest().setContents(ItemSerializer.deserializeItemStacks(r.getEndercontents()));
        p.getInventory().setHeldItemSlot(r.getSelecteditem());
        p.setHealth(r.getHealth());
        for (PotionEffect ef : InvSyncDB.effectsStringToPlayer(r.getPotions())) {
            p.addPotionEffect(ef);
        }
        p.setFoodLevel(r.getFoodlevel());
        p.setSaturation(r.getFoodsaturation().floatValue());
        p.setExhaustion(r.getFoodexhaustion().floatValue());
        p.setExp(r.getExp().floatValue());
        p.setLevel(r.getLevel());
        GameMode gamemode = GameMode.values()[r.getGamemode()];
        if (gamemode != GameMode.SURVIVAL) {
            if (!p.hasPermission("gamemode.creative")) {
                gamemode = GameMode.SURVIVAL;
            }
        }
        p.setGameMode(gamemode);
    }
}
