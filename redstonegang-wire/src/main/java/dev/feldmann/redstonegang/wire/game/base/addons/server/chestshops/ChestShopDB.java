package dev.feldmann.redstonegang.wire.game.base.addons.server.chestshops;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.player.UserDB;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import org.bukkit.inventory.ItemStack;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Timestamp;

public class ChestShopDB extends Database {

    private static Table TABLE = DSL.table(DSL.name("chestshop_history"));

    private static Field<Long> ID = DSL.field(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true));
    private static Field<Timestamp> BUYDATE = DSL.field(DSL.name("buy_date"), SQLDataType.TIMESTAMP.defaultValue(DSL.now()));
    private static Field<Double> VALUE = DSL.field(DSL.name("value"), SQLDataType.DOUBLE.nullable(false));
    private static Field<byte[]> ITEM = DSL.field(DSL.name("item"), SQLDataType.BLOB.nullable(false));
    private static Field<Integer> SHOP_OWNER = DSL.field(DSL.name("shop_owner"), SQLDataType.INTEGER.nullable(false));
    private static Field<Integer> CLIENT = DSL.field(DSL.name("client"), SQLDataType.INTEGER.nullable(false));
    private static Field<Boolean> TYPE = DSL.field(DSL.name("type"), SQLDataType.BOOLEAN.nullable(false));


    public ChestShopDB(String database) {
        super(database);
    }

    @Override
    public void createTables() {
        dsl().createTableIfNotExists(TABLE)
                .columns(ID, VALUE, BUYDATE, ITEM, SHOP_OWNER, CLIENT, TYPE)
                .constraints(
                        DSL.constraint().primaryKey(ID),
                        DSL.foreignKey(SHOP_OWNER).references(UserDB.TABLE, UserDB.ID),
                        DSL.foreignKey(CLIENT).references(UserDB.TABLE, UserDB.ID)
                ).execute();
    }


    public void addEntry(int donoLoja, int cliente, ItemStack item, double valor, boolean compra) {
        dsl()
                .insertInto(TABLE)
                .columns(SHOP_OWNER, CLIENT, ITEM, VALUE, TYPE)
                .values(donoLoja, cliente, ItemSerializer.serializeItemStack(item), valor, compra)
                .executeAsync();

    }
}
