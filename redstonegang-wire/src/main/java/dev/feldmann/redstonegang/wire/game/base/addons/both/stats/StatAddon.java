package dev.feldmann.redstonegang.wire.game.base.addons.both.stats;

import dev.feldmann.redstonegang.wire.game.base.events.player.PlayerJoinServerEvent;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateEvent;
import dev.feldmann.redstonegang.wire.plugin.events.update.UpdateType;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatAddon extends Addon {


    private StatDB db;

    public StatAddon() {
        this.db = new StatDB();
    }

    private List<PlayerStat> stats = new ArrayList();
    private HashMap<Integer, PlayerStatCache> cache = new HashMap<>();


    public void addStat(PlayerStat r) {
        if (!this.stats.contains(r)) {
            this.stats.add(r);
            r.initialize(this);
        }
    }

    @Override
    public void onEnable() {
        loadStats(Stats.class);
        registerListener(new GeneralStatListener(this));
    }

    @Override
    public void onStart() {
        db.createColumns(stats);
        for (PlayerStat r : stats) {
            r.reloadTop();
        }
    }

    protected StatDB getDb() {
        return db;
    }

    @Override
    public void onDisable() {
        db.executePending();
    }

    @EventHandler
    public void update(UpdateEvent ev) {
        if (ev.getType() == UpdateType.SEC_16) {
            runAsync(() -> db.executePending());
        }
    }

    protected PlayerStatCache getCache(int pId) {
        if (!cache.containsKey(pId)) {
            cache.put(pId, db.loadPlayer(stats, pId));
        }
        return cache.get(pId);
    }

    public List<PlayerStat> getStats() {
        return stats;
    }


    public void loadStats(Class ad) {
        for (java.lang.reflect.Field f : ad.getDeclaredFields()) {
            f.setAccessible(true);
            if (PlayerStat.class.isAssignableFrom(f.getType())) {
                try {
                    PlayerStat o = (PlayerStat) f.get(null);
                    if (o != null) {
                        addStat(o);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @EventHandler
    public void join(PlayerJoinServerEvent ev) {
        cache.remove(getPlayerId(ev.getPlayer()));
    }
}
