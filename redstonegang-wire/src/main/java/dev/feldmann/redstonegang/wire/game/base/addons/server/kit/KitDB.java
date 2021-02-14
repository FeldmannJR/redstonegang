package dev.feldmann.redstonegang.wire.game.base.addons.server.kit;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;

import java.util.HashMap;

import static org.jooq.impl.DSL.*;

public class KitDB extends Database {


    public KitDB(String db) {
        super(db);
    }

    private static Table TABLE = table("kits");
    private static Field<String> NAME = field("name", SQLDataType.VARCHAR(100).nullable(false));
    private static Field<byte[]> ITEMS = field("items", SQLDataType.BLOB);
    private static Field<Integer> CD = field("cd", SQLDataType.INTEGER);

    @Override
    public void createTables() {
        dsl().createTableIfNotExists(TABLE)
                .columns(NAME, ITEMS, CD)
                .constraint(constraint().primaryKey(NAME))
                .execute();
    }

    public HashMap<String, Kit> loadKits() {
        HashMap<String, Kit> kits = new HashMap<>();
        Result<Record3<String, byte[], Integer>> rs = dsl().select(NAME, ITEMS, CD).from(TABLE).fetch();
        for (Record3<String, byte[], Integer> r : rs) {
            Kit k = new Kit(r.get(NAME), r.get(CD), ItemSerializer.deserializeItemStacks(r.get(ITEMS)));
            kits.put(k.getName(), k);
        }

        return kits;
    }

    public void saveKit(Kit k) {
        byte[] items = ItemSerializer.serializeItemStacks(k.getItems());
        dsl().insertInto(TABLE)
                .columns(NAME, ITEMS, CD)
                .values(k.getName(), items, k.getCooldown())
                .onDuplicateKeyUpdate().set(ITEMS, items).set(CD, k.getCooldown())
                .execute();
    }

    public void deleteKit(Kit k) {
        dsl().delete(TABLE).where(NAME.eq(k.getName())).execute();
    }
}
