/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addonconfigs.db;


import dev.feldmann.redstonegang.wire.game.base.addonconfigs.db.tables.AddonsConfigs;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>redstonegang_common</code> 
 * schema.
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

    public static final Index ADDONS_CONFIGS_PRIMARY = Indexes0.ADDONS_CONFIGS_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ADDONS_CONFIGS_PRIMARY = Internal.createIndex("PRIMARY", AddonsConfigs.ADDONS_CONFIGS, new OrderField[] { AddonsConfigs.ADDONS_CONFIGS.KEY, AddonsConfigs.ADDONS_CONFIGS.ADDON, AddonsConfigs.ADDONS_CONFIGS.SERVER }, true);
    }
}