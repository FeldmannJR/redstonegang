/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db;


import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.tables.QuestInfos;
import dev.feldmann.redstonegang.wire.game.base.addons.server.quests.db.tables.Quests;

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

    public static final Index QUESTS_PRIMARY = Indexes0.QUESTS_PRIMARY;
    public static final Index QUEST_INFOS_PRIMARY = Indexes0.QUEST_INFOS_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index QUESTS_PRIMARY = Internal.createIndex("PRIMARY", Quests.QUESTS, new OrderField[] { Quests.QUESTS.ID }, true);
        public static Index QUEST_INFOS_PRIMARY = Internal.createIndex("PRIMARY", QuestInfos.QUEST_INFOS, new OrderField[] { QuestInfos.QUEST_INFOS.ID }, true);
    }
}
