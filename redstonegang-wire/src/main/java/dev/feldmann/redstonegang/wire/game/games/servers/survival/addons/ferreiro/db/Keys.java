/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.db;


import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.db.tables.FerreiroItens;
import dev.feldmann.redstonegang.wire.game.games.servers.survival.addons.ferreiro.db.tables.records.FerreiroItensRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>redstonegang_survival</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<FerreiroItensRecord, Long> IDENTITY_FERREIRO_ITENS = Identities0.IDENTITY_FERREIRO_ITENS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<FerreiroItensRecord> KEY_FERREIRO_ITENS_PRIMARY = UniqueKeys0.KEY_FERREIRO_ITENS_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<FerreiroItensRecord, Long> IDENTITY_FERREIRO_ITENS = Internal.createIdentity(FerreiroItens.FERREIRO_ITENS, FerreiroItens.FERREIRO_ITENS.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<FerreiroItensRecord> KEY_FERREIRO_ITENS_PRIMARY = Internal.createUniqueKey(FerreiroItens.FERREIRO_ITENS, "KEY_ferreiro_itens_PRIMARY", FerreiroItens.FERREIRO_ITENS.ID);
    }
}