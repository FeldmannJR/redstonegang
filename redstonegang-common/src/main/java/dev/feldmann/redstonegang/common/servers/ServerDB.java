package dev.feldmann.redstonegang.common.servers;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.ServersRecord;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables;


import java.util.List;

public class ServerDB extends Database {


    @Override
    public void createTables() {

        dsl().execute("CREATE TABLE IF NOT EXISTS servers\n" +
                "(" +
                "    `id`   INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    `name` VARCHAR(60) UNIQUE,\n" +
                "    `alive` TIMESTAMP NULL DEFAULT NULL, \n" +
                "    `started` TIMESTAMP NULL DEFAULT NULL," +
                "    `stopped` TIMESTAMP NULL DEFAULT NULL," +
                "    `game` VARCHAR(32) NULL DEFAULT NULL," +
                "    `joinable` BOOLEAN DEFAULT 0," +
                "    `online` BOOLEAN DEFAULT 0," +
                "    `dev` BOOLEAN DEFAULT 0," +
                "    `host` VARCHAR(60)\n" +
                ")");
    }

    public List<ServersRecord> all() {
        return dsl().selectFrom(Tables.SERVERS).fetch();
    }


    public ServersRecord fetchOrCreate(String name) {
        ServersRecord record = get(name);
        if (record == null) {
            record = dsl().newRecord(Tables.SERVERS);
            record.setName(name);
        }
        return record;
    }


    public ServersRecord register(String name, String host) {
        ServersRecord record = dsl().newRecord(Tables.SERVERS);
        record.setHost(host);
        record.setName(name);
        record.store();
        return record;
    }

    public ServersRecord get(String name) {
        return dsl().fetchAny(Tables.SERVERS, Tables.SERVERS.NAME.eq(name));
    }

    public boolean exists(String name) {
        return dsl().fetchExists(Tables.SERVERS, Tables.SERVERS.NAME.eq(name));
    }

    public boolean delete(ServersRecord record) {
        return record.delete() > 0;
    }


    public void remove(String name) {
        dsl().delete(Tables.SERVERS).where(Tables.SERVERS.NAME.eq(name)).execute();
    }

}
