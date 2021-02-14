package dev.feldmann.redstonegang.wire.game.base.addons.server.economy;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.currencies.CurrencyDouble;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.cmds.EconomyCommand;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.cmds.MoneyTop;
import dev.feldmann.redstonegang.wire.game.base.addons.server.economy.cmds.adm.EconomyAdmCommand;
import dev.feldmann.redstonegang.wire.game.base.objects.Addon;
import dev.feldmann.redstonegang.wire.utils.msgs.C;
import dev.feldmann.redstonegang.common.utils.msgs.MsgType;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class EconomyAddon extends Addon {

    private EconomyDatabase db;
    private String databaseName;
    private String tableName;
    private HashMap<Integer, MoneyCache> cached = new HashMap<>();

    public EconomyAddon(String database, String table) {
        this.databaseName = database;
        this.tableName = table;

    }

    @Override
    public void onEnable() {
        db = new EconomyDatabase(databaseName, tableName);
        registerCommand(new EconomyCommand(this));
        registerCommand(new EconomyAdmCommand(this));
        registerCommand(new MoneyTop(this));
    }

    public CurrencyDouble getCurrency() {
        return db;
    }

    public EconomyDatabase getDb() {
        return db;
    }

    public boolean has(Player p, double money) {
        return has(RedstoneGang.getPlayer(p.getUniqueId()).getId(), money);
    }

    public boolean hasWithMessage(Player p, double money) {
        boolean b = has(RedstoneGang.getPlayer(p.getUniqueId()).getId(), money);
        if (!b)
            C.send(p, MsgType.ERROR, "Você não tem ## para comprar isso!", money);
        return b;
    }

    public void add(Player p, double money) {
        add(RedstoneGang.getPlayer(p.getUniqueId()).getId(), money);
    }

    public void set(Player p, double money) {
        set(RedstoneGang.getPlayer(p.getUniqueId()).getId(), money);
    }

    public void remove(Player p, double money) {
        remove(RedstoneGang.getPlayer(p.getUniqueId()).getId(), money);
    }

    public double get(int playerid) {
        return db.get(playerid);
    }

    public double getCached(int playerId) {
        if (!cached.containsKey(playerId) || cached.get(playerId).expired()) {
            cached.put(playerId, new MoneyCache(get(playerId)));
        }
        return cached.get(playerId).value;
    }

    public void add(int pid, double qtd) {
        new Thread(() -> {
            db.add(pid, qtd);
        }).start();
    }

    public void set(int pid, double qtd) {
        new Thread(() -> {
            db.set(pid, qtd);
        }).start();

    }

    public void remove(int pid, double qtd) {
        new Thread(() -> {
            db.remove(pid, qtd);
        }).start();
    }

    public boolean has(int pid, double money) {
        return db.get(pid) >= money;
    }

    private static class MoneyCache {
        double value;
        long vencimento;

        public MoneyCache(double value) {
            this.value = value;
            this.vencimento = System.currentTimeMillis() + (60 * 1000);
        }

        public boolean expired() {
            return vencimento < System.currentTimeMillis();
        }
    }
}
