package dev.feldmann.redstonegang.wire.game.base.addons.both.chat;

import dev.feldmann.redstonegang.common.db.Database;
import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.db.Tables;

import java.sql.Timestamp;


public class ChatHistoryDB extends Database {


    public ChatHistoryDB(String database) {
        super(database);
    }


    @Override
    public void createTables() {
        dsl().execute("CREATE TABLE IF NOT EXISTS chat_history (`id` BIGINT PRIMARY KEY AUTO_INCREMENT, sender INTEGER, msg VARCHAR(255), channel VARCHAR(10), `when` TIMESTAMP,`server` VARCHAR(30), `receivers` TEXT)");
    }


    public void insert(int playerId, String channel, String msg, String receivers, String server) {
        dsl().insertInto(Tables.CHAT_HISTORY)
                .columns(Tables.CHAT_HISTORY.SENDER, Tables.CHAT_HISTORY.MSG, Tables.CHAT_HISTORY.CHANNEL, Tables.CHAT_HISTORY.WHEN, Tables.CHAT_HISTORY.RECEIVERS, Tables.CHAT_HISTORY.SERVER)
                .values(playerId, msg, channel, new Timestamp(System.currentTimeMillis()), receivers, server)
                .executeAsync();
    }

}
