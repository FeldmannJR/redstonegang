/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addons.both.chat.db;


import dev.feldmann.redstonegang.wire.game.base.addons.both.chat.db.tables.ChatHistory;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code></code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index CHAT_HISTORY_PRIMARY = Indexes0.CHAT_HISTORY_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index CHAT_HISTORY_PRIMARY = Internal.createIndex("PRIMARY", ChatHistory.CHAT_HISTORY, new OrderField[] { ChatHistory.CHAT_HISTORY.ID }, true);
    }
}
