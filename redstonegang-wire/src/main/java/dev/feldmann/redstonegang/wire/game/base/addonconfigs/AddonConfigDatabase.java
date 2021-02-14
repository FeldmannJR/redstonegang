package dev.feldmann.redstonegang.wire.game.base.addonconfigs;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.game.base.addonconfigs.db.tables.records.AddonsConfigsRecord;
import org.jooq.InsertValuesStep4;
import org.jooq.Result;

import java.util.HashMap;

import static dev.feldmann.redstonegang.wire.game.base.addonconfigs.db.Tables.*;

public class AddonConfigDatabase extends Database {

    @Override
    public void createTables() {
        dsl().execute("CREATE TABLE IF NOT EXISTS addons_configs (`key` VARCHAR(64), `addon` VARCHAR(64), `server` VARCHAR(64), `value` TEXT, PRIMARY KEY (`key`,`addon` ,`server`))");
    }

    public HashMap<String, String> loadConfigs(String addon, String server) {
        HashMap<String, String> values = new HashMap<>();
        Result<AddonsConfigsRecord> fetch = dsl().selectFrom(ADDONS_CONFIGS)
                .where(ADDONS_CONFIGS.ADDON.eq(addon))
                .and(ADDONS_CONFIGS.SERVER.eq(server)).fetch();
        for (AddonsConfigsRecord r : fetch) {
            values.put(r.getKey(), r.getValue());
        }
        return values;
    }

    public void save(HashMap<String, String> values, String addon, String server) {
        dsl().deleteFrom(ADDONS_CONFIGS)
                .where(ADDONS_CONFIGS.SERVER.eq(server))
                .and(ADDONS_CONFIGS.ADDON.eq(addon)).execute();
        InsertValuesStep4<AddonsConfigsRecord, String, String, String, String> s = dsl().insertInto(ADDONS_CONFIGS)
                .columns(ADDONS_CONFIGS.ADDON, ADDONS_CONFIGS.SERVER, ADDONS_CONFIGS.KEY, ADDONS_CONFIGS.VALUE);
        for (String key : values.keySet()) {
            String value = values.get(key);
            s = s.values(addon, server, key, value);
        }
        s.execute();
    }

}
