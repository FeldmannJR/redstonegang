package dev.feldmann.redstonegang.wire.game.base.addons.both.simplecmds;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SimplecmdsLocationsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SimplecmdsWarpsRecord;
import dev.feldmann.redstonegang.wire.utils.location.BungeeLocation;

import java.util.HashMap;

import static dev.feldmann.redstonegang.wire.game.base.database.addons.Tables.*;

public class SimpleCmdsDatabase extends Database {
    HashMap<String, BungeeLocation> cacheLocation = new HashMap<>();
    HashMap<String, SimplecmdsWarpsRecord> warps = new HashMap<>();


    public SimpleCmdsDatabase(String database) {
        super(database);
    }

    @Override
    public void createTables() {
        dsl().execute("CREATE TABLE IF NOT EXISTS `simplecmds_locations`\n" +
                "(\n" +
                "    `id`       INTEGER AUTO_INCREMENT PRIMARY KEY ,\n" +
                "    `name`     VARCHAR(64) NOT NULL UNIQUE,\n" +
                "    `location` VARCHAR(255)\n" +
                ");");
        dsl().execute("CREATE TABLE IF NOT EXISTS `simplecmds_warps` (\n" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(64) NOT NULL,\n" +
                "  `location` varchar(255) DEFAULT NULL,\n" +
                "  `public` tinyint(1) DEFAULT '0',\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `name` (`name`)\n" +
                ") ENGINE=InnoDB ");
    }


    public void setLocation(String key, BungeeLocation location) {
        SimplecmdsLocationsRecord entry = dsl().selectFrom(SIMPLECMDS_LOCATIONS).where(SIMPLECMDS_LOCATIONS.NAME.eq(key)).fetchAny();
        if (entry == null) {
            entry = dsl().newRecord(SIMPLECMDS_LOCATIONS);
            entry.setName(key);
        }
        entry.setLocation(location.toString());
        entry.store();
        cacheLocation.put(key, location);
    }

    public BungeeLocation getLocation(String key) {
        if (!cacheLocation.containsKey(key)) {
            SimplecmdsLocationsRecord entry = dsl().selectFrom(SIMPLECMDS_LOCATIONS).where(SIMPLECMDS_LOCATIONS.NAME.eq(key)).fetchAny();
            if (entry == null) {
                cacheLocation.put(key, null);
            } else {
                cacheLocation.put(key, BungeeLocation.fromString(entry.getLocation()));
            }
        }
        return cacheLocation.get(key);
    }

    public void setWarp(String key, BungeeLocation location, boolean isPublic) {
        SimplecmdsWarpsRecord entry = dsl().selectFrom(SIMPLECMDS_WARPS).where(SIMPLECMDS_WARPS.NAME.eq(key)).fetchAny();
        if (entry == null) {
            entry = dsl().newRecord(SIMPLECMDS_WARPS);
            entry.setName(key);
        }
        entry.setPublic(isPublic);
        entry.setLocation(location.toString());
        entry.store();
        warps.put(key, entry);
    }

    public void deleteWarp(String name) {
        warps.remove(name);
        dsl().deleteFrom(SIMPLECMDS_WARPS).where(SIMPLECMDS_WARPS.NAME.eq(name)).execute();
    }

    public void loadAllWarps() {
        for (SimplecmdsWarpsRecord record : dsl().selectFrom(SIMPLECMDS_WARPS).fetch()) {
            warps.put(record.getName(), record);
        }
    }

    public SimplecmdsWarpsRecord getWarp(String key) {
        if (!warps.containsKey(key)) {
            SimplecmdsWarpsRecord entry = dsl().selectFrom(SIMPLECMDS_WARPS).where(SIMPLECMDS_WARPS.NAME.eq(key)).fetchAny();
            if (entry != null) {
                warps.put(key, entry);
            }
        }
        return warps.get(key);
    }


}
