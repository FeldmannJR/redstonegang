package dev.feldmann.redstonegang.wire.game.base.addons.both.customBlocks;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.utils.json.RGson;
import dev.feldmann.redstonegang.wire.utils.location.BungeeBlock;
import com.google.gson.JsonObject;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.util.HashMap;


public class CustomBlocksDB extends Database {

    private Table TABLE;
    private Field<String> BLOCK = DSL.field("block", SQLDataType.VARCHAR(200).nullable(false));
    private Field<String> BLOCK_DATA_TYPE = DSL.field("block_data_type", SQLDataType.VARCHAR(100));
    private Field<String> BLOCK_DATA = DSL.field("block_data", SQLDataType.VARCHAR(3000));

    private String tableName = null;
    private String database;
    private CustomBlocksAddon manager;

    public CustomBlocksDB(CustomBlocksAddon manager, String database, String tableName) {
        super(database);
        this.tableName = tableName;
        this.database = database;
        TABLE = DSL.table(tableName);
        this.manager = manager;
    }

    @Override
    public void createTables() {
        if (tableName == null) return;
        dsl().createTableIfNotExists(TABLE)
                .columns(BLOCK, BLOCK_DATA_TYPE, BLOCK_DATA)
                .constraint(DSL.constraint().primaryKey(BLOCK))
                .execute();

    }

    public HashMap<BungeeBlock, BlockData> loadBlocks() {

        HashMap<BungeeBlock, BlockData> blocks = new HashMap<>();

        Result<Record> rs = dsl().select().from(TABLE).where(BLOCK.startsWith(RedstoneGang.instance.getNomeServer() + ";")).fetch();
        for (Record r : rs) {
            String dat = r.get(BLOCK_DATA);
            BlockData data = manager.create(BungeeBlock.fromString(r.get(BLOCK)), r.get(BLOCK_DATA_TYPE), dat != null ? dat : "");
            if (data == null) continue;
            blocks.put(data.getLoc(), data);
            data.change = false;
        }
        return blocks;

    }

    public void delete(BlockData block) {
        dsl().deleteFrom(TABLE).where(BLOCK.eq(block.getLoc().toString())).execute();
        block.deleted = true;
    }

    public void save(BlockData block) {
        JsonObject json = new JsonObject();
        block.write(json);
        String data = RGson.gson().toJson(json);
        String type = getType(block);
        if (type == null) return;
        dsl().insertInto(TABLE).
                columns(BLOCK, BLOCK_DATA_TYPE, BLOCK_DATA)
                .values(block.getLoc().toString(), type, data)
                .onDuplicateKeyUpdate()
                .set(BLOCK_DATA_TYPE, type)
                .set(BLOCK_DATA, data)
                .execute();
    }

    public String getType(BlockData b) {
        for (String t : manager.types.keySet()) {
            if (manager.types.get(t) == b.getClass()) {
                return t;
            }
        }
        return null;
    }


}
