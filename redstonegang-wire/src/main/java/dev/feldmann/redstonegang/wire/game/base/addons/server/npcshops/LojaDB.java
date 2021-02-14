package dev.feldmann.redstonegang.wire.game.base.addons.server.npcshops;


import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import org.bukkit.inventory.ItemStack;
import org.jooq.*;

import static org.jooq.impl.DSL.*;

import org.jooq.impl.SQLDataType;

import java.util.HashMap;
import java.util.UUID;

public class LojaDB extends Database {

    public LojaDB(String database) {
        super(database);
    }

    private static final Table TABLE = table("lojas_npcs");
    private static final Field<String> UUID_FIELD = field("uuid", SQLDataType.VARCHAR(60).nullable(false));
    private static final Field<String> PERMISSION = field("permission", SQLDataType.VARCHAR(60).nullable(true));
    private static final Field<byte[]> ITEMS = field("items", SQLDataType.BLOB);


    @Override
    public void createTables() {
        dsl().
                createTableIfNotExists(TABLE)
                .column(UUID_FIELD)
                .column(ITEMS)
                .column(PERMISSION)
                .constraint(constraint().primaryKey(UUID_FIELD))
                .execute();
    }


    public HashMap<UUID, LojaNPC> loadLojas() {
        HashMap<UUID, LojaNPC> npc = new HashMap();

        Result<Record3<String, String, byte[]>> f = dsl().select(UUID_FIELD, PERMISSION, ITEMS).from(TABLE).fetch();
        for (Record3<String, String, byte[]> loja : f) {
            UUID uuid = UUID.fromString(loja.get(UUID_FIELD));
            ItemStack[] it = ItemSerializer.deserializeItemStacks(loja.get(ITEMS));
            npc.put(uuid, LojaNPC.fromItems(uuid, loja.get(PERMISSION), it));
        }
        return npc;
    }

    public void saveLoja(LojaNPC loja) {
        dsl()
                .insertInto(TABLE)
                .columns(UUID_FIELD, ITEMS)
                .values(loja.getUUID().toString(), ItemSerializer.serializeItemStacks(loja.toItemStackArray()))
                .onDuplicateKeyUpdate().set(ITEMS, ItemSerializer.serializeItemStacks(loja.toItemStackArray()))
                .execute();
    }

    public void deleteLoja(LojaNPC loja) {
        dsl()
                .deleteFrom(TABLE)
                .where(UUID_FIELD.eq(loja.getUUID().toString()))
                .execute();
    }

}
