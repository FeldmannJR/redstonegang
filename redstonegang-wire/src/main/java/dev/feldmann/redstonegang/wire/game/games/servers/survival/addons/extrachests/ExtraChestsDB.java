package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.extrachests;

import dev.feldmann.redstonegang.common.db.DatabaseHelper;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.SurvivalDatabase;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import org.bukkit.inventory.ItemStack;
import org.jooq.*;
import org.jooq.impl.SQLDataType;

import java.util.HashMap;

import static org.jooq.impl.DSL.*;

public class ExtraChestsDB extends SurvivalDatabase {


    private static Field<Integer> PLAYER_ID = field("id", SQLDataType.INTEGER.nullable(false));
    private static Table TABLE = table(name("extrachests"));

    private ExtraChests manager;

    public ExtraChestsDB(ExtraChests extraChests) {
        this.manager = extraChests;
    }

    @Override
    public void createTables() {
        dsl()
                .createTableIfNotExists(TABLE)
                .columns(PLAYER_ID)
                .constraint(constraint().primaryKey(PLAYER_ID))
                .execute();

        for (ChestList c : ChestList.values()) {
            String cname = c.name().toLowerCase();
            if (!DatabaseHelper.columnExists(getConn(), TABLE.getName(), cname)) {
                System.out.println("Criando coluna " + cname);
                dsl().alterTable(TABLE).addColumn(c.getField()).execute();
            }

        }
    }

    public PlayerChestData load(int id) {
        Record r = dsl().select().from(TABLE).where(PLAYER_ID.eq(id)).fetchOne();
        HashMap<ChestList, ChestData> its = new HashMap();


        for (ChestList c : ChestList.values()) {
            ChestData data;
            if (r != null) {
                byte[] b = r.get(c.getField());
                ItemStack[] items;
                if (b != null) {
                    items = ItemSerializer.deserializeItemStacks(b);
                } else {
                    items = new ItemStack[c.getSize()];
                }
                data = new ChestData(id, c, items);
            } else {
                data = new ChestData(id, c, new ItemStack[c.getSize()]);
            }
            data.manager = manager;
            its.put(c, data);

        }

        return new PlayerChestData(its);
    }

    public void save(int pid, ChestList c, ItemStack[] itens) {

        dsl()
                .insertInto(TABLE)
                .columns(PLAYER_ID, c.getField())
                .values(pid, ItemSerializer.serializeItemStacks(itens))
                .onDuplicateKeyUpdate().set(c.getField(), ItemSerializer.serializeItemStacks(itens)).execute();
    }


}
