/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.database.addons;


import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.FloatshopItems;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.FloatshopShops;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.FloatshopTransactions;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.MultiserverTpRequests;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.MultiserverTpTeleports;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.PlayerInventories;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.Safetime;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.SimplecmdsLocations;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.SimplecmdsWarps;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.Terrenos;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.TerrenosPlayers;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.Whitelistcmd;

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

    public static final Index FLOATSHOP_ITEMS_PRIMARY = Indexes0.FLOATSHOP_ITEMS_PRIMARY;
    public static final Index FLOATSHOP_ITEMS_SHOP_ID = Indexes0.FLOATSHOP_ITEMS_SHOP_ID;
    public static final Index FLOATSHOP_SHOPS_NPC_UUID = Indexes0.FLOATSHOP_SHOPS_NPC_UUID;
    public static final Index FLOATSHOP_SHOPS_PRIMARY = Indexes0.FLOATSHOP_SHOPS_PRIMARY;
    public static final Index FLOATSHOP_TRANSACTIONS_PRIMARY = Indexes0.FLOATSHOP_TRANSACTIONS_PRIMARY;
    public static final Index MULTISERVER_TP_REQUESTS_PRIMARY = Indexes0.MULTISERVER_TP_REQUESTS_PRIMARY;
    public static final Index MULTISERVER_TP_REQUESTS_REQUESTED_FK = Indexes0.MULTISERVER_TP_REQUESTS_REQUESTED_FK;
    public static final Index MULTISERVER_TP_REQUESTS_REQUEST_UNIQUE_FK = Indexes0.MULTISERVER_TP_REQUESTS_REQUEST_UNIQUE_FK;
    public static final Index MULTISERVER_TP_TELEPORTS_PRIMARY = Indexes0.MULTISERVER_TP_TELEPORTS_PRIMARY;
    public static final Index MULTISERVER_TP_TELEPORTS_TARGET_FK = Indexes0.MULTISERVER_TP_TELEPORTS_TARGET_FK;
    public static final Index PLAYER_INVENTORIES_PRIMARY = Indexes0.PLAYER_INVENTORIES_PRIMARY;
    public static final Index SAFETIME_PRIMARY = Indexes0.SAFETIME_PRIMARY;
    public static final Index SAFETIME_USER_ID = Indexes0.SAFETIME_USER_ID;
    public static final Index SIMPLECMDS_LOCATIONS_NAME = Indexes0.SIMPLECMDS_LOCATIONS_NAME;
    public static final Index SIMPLECMDS_LOCATIONS_PRIMARY = Indexes0.SIMPLECMDS_LOCATIONS_PRIMARY;
    public static final Index SIMPLECMDS_WARPS_NAME = Indexes0.SIMPLECMDS_WARPS_NAME;
    public static final Index SIMPLECMDS_WARPS_PRIMARY = Indexes0.SIMPLECMDS_WARPS_PRIMARY;
    public static final Index TERRENOS_PRIMARY = Indexes0.TERRENOS_PRIMARY;
    public static final Index TERRENOS_PLAYERS_PRIMARY = Indexes0.TERRENOS_PLAYERS_PRIMARY;
    public static final Index TERRENOS_PLAYERS_TERRENO_ENTRY = Indexes0.TERRENOS_PLAYERS_TERRENO_ENTRY;
    public static final Index WHITELISTCMD_PRIMARY = Indexes0.WHITELISTCMD_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index FLOATSHOP_ITEMS_PRIMARY = Internal.createIndex("PRIMARY", FloatshopItems.FLOATSHOP_ITEMS, new OrderField[] { FloatshopItems.FLOATSHOP_ITEMS.ID }, true);
        public static Index FLOATSHOP_ITEMS_SHOP_ID = Internal.createIndex("shop_id", FloatshopItems.FLOATSHOP_ITEMS, new OrderField[] { FloatshopItems.FLOATSHOP_ITEMS.SHOP_ID }, false);
        public static Index FLOATSHOP_SHOPS_NPC_UUID = Internal.createIndex("npc_uuid", FloatshopShops.FLOATSHOP_SHOPS, new OrderField[] { FloatshopShops.FLOATSHOP_SHOPS.NPC_UUID }, true);
        public static Index FLOATSHOP_SHOPS_PRIMARY = Internal.createIndex("PRIMARY", FloatshopShops.FLOATSHOP_SHOPS, new OrderField[] { FloatshopShops.FLOATSHOP_SHOPS.ID }, true);
        public static Index FLOATSHOP_TRANSACTIONS_PRIMARY = Internal.createIndex("PRIMARY", FloatshopTransactions.FLOATSHOP_TRANSACTIONS, new OrderField[] { FloatshopTransactions.FLOATSHOP_TRANSACTIONS.ID }, true);
        public static Index MULTISERVER_TP_REQUESTS_PRIMARY = Internal.createIndex("PRIMARY", MultiserverTpRequests.MULTISERVER_TP_REQUESTS, new OrderField[] { MultiserverTpRequests.MULTISERVER_TP_REQUESTS.ID }, true);
        public static Index MULTISERVER_TP_REQUESTS_REQUESTED_FK = Internal.createIndex("requested_fk", MultiserverTpRequests.MULTISERVER_TP_REQUESTS, new OrderField[] { MultiserverTpRequests.MULTISERVER_TP_REQUESTS.REQUESTED }, false);
        public static Index MULTISERVER_TP_REQUESTS_REQUEST_UNIQUE_FK = Internal.createIndex("request_unique_fk", MultiserverTpRequests.MULTISERVER_TP_REQUESTS, new OrderField[] { MultiserverTpRequests.MULTISERVER_TP_REQUESTS.REQUESTER, MultiserverTpRequests.MULTISERVER_TP_REQUESTS.REQUESTED }, true);
        public static Index MULTISERVER_TP_TELEPORTS_PRIMARY = Internal.createIndex("PRIMARY", MultiserverTpTeleports.MULTISERVER_TP_TELEPORTS, new OrderField[] { MultiserverTpTeleports.MULTISERVER_TP_TELEPORTS.TELEPORTER }, true);
        public static Index MULTISERVER_TP_TELEPORTS_TARGET_FK = Internal.createIndex("target_fk", MultiserverTpTeleports.MULTISERVER_TP_TELEPORTS, new OrderField[] { MultiserverTpTeleports.MULTISERVER_TP_TELEPORTS.TARGET }, false);
        public static Index PLAYER_INVENTORIES_PRIMARY = Internal.createIndex("PRIMARY", PlayerInventories.PLAYER_INVENTORIES, new OrderField[] { PlayerInventories.PLAYER_INVENTORIES.ID }, true);
        public static Index SAFETIME_PRIMARY = Internal.createIndex("PRIMARY", Safetime.SAFETIME, new OrderField[] { Safetime.SAFETIME.ID }, true);
        public static Index SAFETIME_USER_ID = Internal.createIndex("user_id", Safetime.SAFETIME, new OrderField[] { Safetime.SAFETIME.USER_ID }, true);
        public static Index SIMPLECMDS_LOCATIONS_NAME = Internal.createIndex("name", SimplecmdsLocations.SIMPLECMDS_LOCATIONS, new OrderField[] { SimplecmdsLocations.SIMPLECMDS_LOCATIONS.NAME }, true);
        public static Index SIMPLECMDS_LOCATIONS_PRIMARY = Internal.createIndex("PRIMARY", SimplecmdsLocations.SIMPLECMDS_LOCATIONS, new OrderField[] { SimplecmdsLocations.SIMPLECMDS_LOCATIONS.ID }, true);
        public static Index SIMPLECMDS_WARPS_NAME = Internal.createIndex("name", SimplecmdsWarps.SIMPLECMDS_WARPS, new OrderField[] { SimplecmdsWarps.SIMPLECMDS_WARPS.NAME }, true);
        public static Index SIMPLECMDS_WARPS_PRIMARY = Internal.createIndex("PRIMARY", SimplecmdsWarps.SIMPLECMDS_WARPS, new OrderField[] { SimplecmdsWarps.SIMPLECMDS_WARPS.ID }, true);
        public static Index TERRENOS_PRIMARY = Internal.createIndex("PRIMARY", Terrenos.TERRENOS, new OrderField[] { Terrenos.TERRENOS.ID }, true);
        public static Index TERRENOS_PLAYERS_PRIMARY = Internal.createIndex("PRIMARY", TerrenosPlayers.TERRENOS_PLAYERS, new OrderField[] { TerrenosPlayers.TERRENOS_PLAYERS.ID }, true);
        public static Index TERRENOS_PLAYERS_TERRENO_ENTRY = Internal.createIndex("terreno_entry", TerrenosPlayers.TERRENOS_PLAYERS, new OrderField[] { TerrenosPlayers.TERRENOS_PLAYERS.TERRENO_ID, TerrenosPlayers.TERRENOS_PLAYERS.TYPE, TerrenosPlayers.TERRENOS_PLAYERS.PLAYER_ID }, true);
        public static Index WHITELISTCMD_PRIMARY = Internal.createIndex("PRIMARY", Whitelistcmd.WHITELISTCMD, new OrderField[] { Whitelistcmd.WHITELISTCMD.COMMAND }, true);
    }
}
