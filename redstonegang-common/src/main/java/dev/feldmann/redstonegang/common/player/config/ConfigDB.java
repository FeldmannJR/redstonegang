package dev.feldmann.redstonegang.common.player.config;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.player.UserDB;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;

import java.util.Collection;

import static org.jooq.impl.DSL.*;

public class ConfigDB extends Database {

    private static final Table TABLE = table("player_configs");
    private static final Field<Integer> PLAYER_ID = field("id", SQLDataType.INTEGER.nullable(false));


    private ConfigManager manager;

    public ConfigDB(ConfigManager manager) {
        this.manager = manager;
    }

    @Override
    public void createTables() {
        dsl().createTableIfNotExists(TABLE)
                .columns(PLAYER_ID)
                .constraints(
                        primaryKey(PLAYER_ID),
                        foreignKey(PLAYER_ID).references(UserDB.TABLE, UserDB.ID)
                )
                .execute();
    }

    public void alterTables() {
        Collection<ConfigType> t = manager.configs.values();
        for (ConfigType type : t) {
            type.loaded = true;
            type.alterTable(this, TABLE);
        }
    }

    public PlayerConfig load(int pid) {
        Record r = dsl().select().from(TABLE).where(PLAYER_ID.eq(pid)).fetchOne();
        PlayerConfig config = new PlayerConfig(manager, pid);
        if (r != null) {
            for (ConfigType type : manager.configs.values()) {
                config.load(type, type.getValue(r));
            }
        }
        return config;
    }

    public <T, D> void set(PlayerConfig conf, ConfigType<T, D> type, T value) {
        dsl().insertInto(TABLE)
                .columns(PLAYER_ID, type.getField())
                .values(conf.pid, type.convertToDb(value))
                .onDuplicateKeyUpdate()
                .set(type.getField(), type.convertToDb(value))
                .execute();

    }

}
