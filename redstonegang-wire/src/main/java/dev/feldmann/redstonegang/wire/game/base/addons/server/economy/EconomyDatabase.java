package dev.feldmann.redstonegang.wire.game.base.addons.server.economy;

import dev.feldmann.redstonegang.common.currencies.CurrencyDouble;
import dev.feldmann.redstonegang.common.currencies.CurrencyMaterial;
import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.money.CurrencyDoubleDatabase;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.jooq.*;
import org.jooq.impl.SQLDataType;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import static org.jooq.impl.DSL.*;

public class EconomyDatabase extends Database implements CurrencyDouble, CurrencyDoubleDatabase {
    private String table;

    private Table TABLE;

    private final Field<Long> T_ID = field(name(table + "_transferencias", "id"), SQLDataType.BIGINT.nullable(false).identity(true));
    private final Field<Integer> T_SEND_PLAYER_ID = field(name(table + "_transferencias", "player_send"), SQLDataType.INTEGER.nullable(false));
    private final Field<Integer> T_RECEIVED_PLAYER_ID = field(name(table + "_transferencias", "player_received"), SQLDataType.INTEGER.nullable(false));
    private final Field<Double> T_QUANTITY = field(name(table + "_transferencias", "quantity"), SQLDataType.DOUBLE.nullable(false));
    private final Field<Timestamp> T_TRANSFER_TIME = field(name(table + "_transferencias", "transfer_time"), SQLDataType.TIMESTAMP.nullable(false));

    private Table TRANSFERENCIAS;

    public EconomyDatabase(String database, String table) {
        super(database);
        this.table = table;
        TABLE = table(table);
        TRANSFERENCIAS = table(table + "_transferencias");

        createMoneyTable();


        dsl().createTableIfNotExists(TRANSFERENCIAS)
                .columns(T_ID, T_SEND_PLAYER_ID, T_RECEIVED_PLAYER_ID, T_QUANTITY, T_TRANSFER_TIME)
                .constraint(constraint().primaryKey(T_ID))
                .execute();

    }

    public void addTransfer(int send_player, int received_player, double qtd) {
        dsl().insertInto(TRANSFERENCIAS).columns(T_SEND_PLAYER_ID, T_RECEIVED_PLAYER_ID, T_QUANTITY).values(send_player, received_player, qtd).executeAsync();
    }

    @Override
    public void createTables() {

    }

    @Override
    public String getTableName() {
        return table;
    }

    @Override
    public DSLContext getDsl() {
        return dsl();
    }

    @Override
    public String getMoedaName(int i) {
        return getMoedaName() + (i != 1 ? "s" : "");
    }

    @Override
    public String getMoedaName() {
        return "moeda";
    }

    @Override
    public String getColor() {
        return ChatColor.GOLD + "";
    }

    @Override
    public String getIdentifier() {
        return "coins";
    }

    @Override
    public CurrencyMaterial getMaterial() {
        return new CurrencyMaterial(Material.DOUBLE_PLANT.getId(), (byte) 0);
    }


}
