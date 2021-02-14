package dev.feldmann.redstonegang.common.logs;

import dev.feldmann.redstonegang.common.RedstoneGang;
import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.common.currencies.Currency;

import java.sql.Timestamp;

import static dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Tables.*;

public class Logs extends Database {
    @Override
    public void createTables() {

        dsl().execute("CREATE TABLE IF NOT EXISTS moeda_logs " +
                "(`id` BIGINT auto_increment primary key, " +
                "`playerId` INTEGER, " +
                "`value` INTEGER, " +
                "`moeda` VARCHAR(255), " +
                "`server` VARCHAR(255), " +
                "`desc` VARCHAR(255), " +
                "`when` TIMESTAMP);    ");
    }

    public void insertMoeda(int pid, String desc, int qtd, Currency m) {
        dsl().insertInto(MOEDA_LOGS).columns(MOEDA_LOGS.PLAYERID, MOEDA_LOGS.DESC, MOEDA_LOGS.MOEDA, MOEDA_LOGS.VALUE, MOEDA_LOGS.WHEN, MOEDA_LOGS.SERVER)
                .values(pid, desc, m.getIdentifier(), qtd, new Timestamp(System.currentTimeMillis()), RedstoneGang.instance.getNomeServer()).executeAsync();
    }


}
