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
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.FloatshopItemsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.FloatshopShopsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.FloatshopTransactionsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.MultiserverTpRequestsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.MultiserverTpTeleportsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.PlayerInventoriesRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SafetimeRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SimplecmdsLocationsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.SimplecmdsWarpsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.TerrenosPlayersRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.TerrenosRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.WhitelistcmdRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code></code> schema.
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

    public static final Identity<FloatshopItemsRecord, Integer> IDENTITY_FLOATSHOP_ITEMS = Identities0.IDENTITY_FLOATSHOP_ITEMS;
    public static final Identity<FloatshopShopsRecord, Integer> IDENTITY_FLOATSHOP_SHOPS = Identities0.IDENTITY_FLOATSHOP_SHOPS;
    public static final Identity<FloatshopTransactionsRecord, Long> IDENTITY_FLOATSHOP_TRANSACTIONS = Identities0.IDENTITY_FLOATSHOP_TRANSACTIONS;
    public static final Identity<MultiserverTpRequestsRecord, Long> IDENTITY_MULTISERVER_TP_REQUESTS = Identities0.IDENTITY_MULTISERVER_TP_REQUESTS;
    public static final Identity<SafetimeRecord, Integer> IDENTITY_SAFETIME = Identities0.IDENTITY_SAFETIME;
    public static final Identity<SimplecmdsLocationsRecord, Integer> IDENTITY_SIMPLECMDS_LOCATIONS = Identities0.IDENTITY_SIMPLECMDS_LOCATIONS;
    public static final Identity<SimplecmdsWarpsRecord, Integer> IDENTITY_SIMPLECMDS_WARPS = Identities0.IDENTITY_SIMPLECMDS_WARPS;
    public static final Identity<TerrenosRecord, Integer> IDENTITY_TERRENOS = Identities0.IDENTITY_TERRENOS;
    public static final Identity<TerrenosPlayersRecord, Integer> IDENTITY_TERRENOS_PLAYERS = Identities0.IDENTITY_TERRENOS_PLAYERS;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<FloatshopItemsRecord> KEY_FLOATSHOP_ITEMS_PRIMARY = UniqueKeys0.KEY_FLOATSHOP_ITEMS_PRIMARY;
    public static final UniqueKey<FloatshopShopsRecord> KEY_FLOATSHOP_SHOPS_PRIMARY = UniqueKeys0.KEY_FLOATSHOP_SHOPS_PRIMARY;
    public static final UniqueKey<FloatshopShopsRecord> KEY_FLOATSHOP_SHOPS_NPC_UUID = UniqueKeys0.KEY_FLOATSHOP_SHOPS_NPC_UUID;
    public static final UniqueKey<FloatshopTransactionsRecord> KEY_FLOATSHOP_TRANSACTIONS_PRIMARY = UniqueKeys0.KEY_FLOATSHOP_TRANSACTIONS_PRIMARY;
    public static final UniqueKey<MultiserverTpRequestsRecord> KEY_MULTISERVER_TP_REQUESTS_PRIMARY = UniqueKeys0.KEY_MULTISERVER_TP_REQUESTS_PRIMARY;
    public static final UniqueKey<MultiserverTpRequestsRecord> KEY_MULTISERVER_TP_REQUESTS_REQUEST_UNIQUE_FK = UniqueKeys0.KEY_MULTISERVER_TP_REQUESTS_REQUEST_UNIQUE_FK;
    public static final UniqueKey<MultiserverTpTeleportsRecord> KEY_MULTISERVER_TP_TELEPORTS_PRIMARY = UniqueKeys0.KEY_MULTISERVER_TP_TELEPORTS_PRIMARY;
    public static final UniqueKey<PlayerInventoriesRecord> KEY_PLAYER_INVENTORIES_PRIMARY = UniqueKeys0.KEY_PLAYER_INVENTORIES_PRIMARY;
    public static final UniqueKey<SafetimeRecord> KEY_SAFETIME_PRIMARY = UniqueKeys0.KEY_SAFETIME_PRIMARY;
    public static final UniqueKey<SafetimeRecord> KEY_SAFETIME_USER_ID = UniqueKeys0.KEY_SAFETIME_USER_ID;
    public static final UniqueKey<SimplecmdsLocationsRecord> KEY_SIMPLECMDS_LOCATIONS_PRIMARY = UniqueKeys0.KEY_SIMPLECMDS_LOCATIONS_PRIMARY;
    public static final UniqueKey<SimplecmdsLocationsRecord> KEY_SIMPLECMDS_LOCATIONS_NAME = UniqueKeys0.KEY_SIMPLECMDS_LOCATIONS_NAME;
    public static final UniqueKey<SimplecmdsWarpsRecord> KEY_SIMPLECMDS_WARPS_PRIMARY = UniqueKeys0.KEY_SIMPLECMDS_WARPS_PRIMARY;
    public static final UniqueKey<SimplecmdsWarpsRecord> KEY_SIMPLECMDS_WARPS_NAME = UniqueKeys0.KEY_SIMPLECMDS_WARPS_NAME;
    public static final UniqueKey<TerrenosRecord> KEY_TERRENOS_PRIMARY = UniqueKeys0.KEY_TERRENOS_PRIMARY;
    public static final UniqueKey<TerrenosPlayersRecord> KEY_TERRENOS_PLAYERS_PRIMARY = UniqueKeys0.KEY_TERRENOS_PLAYERS_PRIMARY;
    public static final UniqueKey<TerrenosPlayersRecord> KEY_TERRENOS_PLAYERS_TERRENO_ENTRY = UniqueKeys0.KEY_TERRENOS_PLAYERS_TERRENO_ENTRY;
    public static final UniqueKey<WhitelistcmdRecord> KEY_WHITELISTCMD_PRIMARY = UniqueKeys0.KEY_WHITELISTCMD_PRIMARY;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<FloatshopItemsRecord, FloatshopShopsRecord> FLOATSHOP_ITEMS_IBFK_1 = ForeignKeys0.FLOATSHOP_ITEMS_IBFK_1;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<FloatshopItemsRecord, Integer> IDENTITY_FLOATSHOP_ITEMS = Internal.createIdentity(FloatshopItems.FLOATSHOP_ITEMS, FloatshopItems.FLOATSHOP_ITEMS.ID);
        public static Identity<FloatshopShopsRecord, Integer> IDENTITY_FLOATSHOP_SHOPS = Internal.createIdentity(FloatshopShops.FLOATSHOP_SHOPS, FloatshopShops.FLOATSHOP_SHOPS.ID);
        public static Identity<FloatshopTransactionsRecord, Long> IDENTITY_FLOATSHOP_TRANSACTIONS = Internal.createIdentity(FloatshopTransactions.FLOATSHOP_TRANSACTIONS, FloatshopTransactions.FLOATSHOP_TRANSACTIONS.ID);
        public static Identity<MultiserverTpRequestsRecord, Long> IDENTITY_MULTISERVER_TP_REQUESTS = Internal.createIdentity(MultiserverTpRequests.MULTISERVER_TP_REQUESTS, MultiserverTpRequests.MULTISERVER_TP_REQUESTS.ID);
        public static Identity<SafetimeRecord, Integer> IDENTITY_SAFETIME = Internal.createIdentity(Safetime.SAFETIME, Safetime.SAFETIME.ID);
        public static Identity<SimplecmdsLocationsRecord, Integer> IDENTITY_SIMPLECMDS_LOCATIONS = Internal.createIdentity(SimplecmdsLocations.SIMPLECMDS_LOCATIONS, SimplecmdsLocations.SIMPLECMDS_LOCATIONS.ID);
        public static Identity<SimplecmdsWarpsRecord, Integer> IDENTITY_SIMPLECMDS_WARPS = Internal.createIdentity(SimplecmdsWarps.SIMPLECMDS_WARPS, SimplecmdsWarps.SIMPLECMDS_WARPS.ID);
        public static Identity<TerrenosRecord, Integer> IDENTITY_TERRENOS = Internal.createIdentity(Terrenos.TERRENOS, Terrenos.TERRENOS.ID);
        public static Identity<TerrenosPlayersRecord, Integer> IDENTITY_TERRENOS_PLAYERS = Internal.createIdentity(TerrenosPlayers.TERRENOS_PLAYERS, TerrenosPlayers.TERRENOS_PLAYERS.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<FloatshopItemsRecord> KEY_FLOATSHOP_ITEMS_PRIMARY = Internal.createUniqueKey(FloatshopItems.FLOATSHOP_ITEMS, "KEY_floatshop_items_PRIMARY", FloatshopItems.FLOATSHOP_ITEMS.ID);
        public static final UniqueKey<FloatshopShopsRecord> KEY_FLOATSHOP_SHOPS_PRIMARY = Internal.createUniqueKey(FloatshopShops.FLOATSHOP_SHOPS, "KEY_floatshop_shops_PRIMARY", FloatshopShops.FLOATSHOP_SHOPS.ID);
        public static final UniqueKey<FloatshopShopsRecord> KEY_FLOATSHOP_SHOPS_NPC_UUID = Internal.createUniqueKey(FloatshopShops.FLOATSHOP_SHOPS, "KEY_floatshop_shops_npc_uuid", FloatshopShops.FLOATSHOP_SHOPS.NPC_UUID);
        public static final UniqueKey<FloatshopTransactionsRecord> KEY_FLOATSHOP_TRANSACTIONS_PRIMARY = Internal.createUniqueKey(FloatshopTransactions.FLOATSHOP_TRANSACTIONS, "KEY_floatshop_transactions_PRIMARY", FloatshopTransactions.FLOATSHOP_TRANSACTIONS.ID);
        public static final UniqueKey<MultiserverTpRequestsRecord> KEY_MULTISERVER_TP_REQUESTS_PRIMARY = Internal.createUniqueKey(MultiserverTpRequests.MULTISERVER_TP_REQUESTS, "KEY_multiserver_tp_requests_PRIMARY", MultiserverTpRequests.MULTISERVER_TP_REQUESTS.ID);
        public static final UniqueKey<MultiserverTpRequestsRecord> KEY_MULTISERVER_TP_REQUESTS_REQUEST_UNIQUE_FK = Internal.createUniqueKey(MultiserverTpRequests.MULTISERVER_TP_REQUESTS, "KEY_multiserver_tp_requests_request_unique_fk", MultiserverTpRequests.MULTISERVER_TP_REQUESTS.REQUESTER, MultiserverTpRequests.MULTISERVER_TP_REQUESTS.REQUESTED);
        public static final UniqueKey<MultiserverTpTeleportsRecord> KEY_MULTISERVER_TP_TELEPORTS_PRIMARY = Internal.createUniqueKey(MultiserverTpTeleports.MULTISERVER_TP_TELEPORTS, "KEY_multiserver_tp_teleports_PRIMARY", MultiserverTpTeleports.MULTISERVER_TP_TELEPORTS.TELEPORTER);
        public static final UniqueKey<PlayerInventoriesRecord> KEY_PLAYER_INVENTORIES_PRIMARY = Internal.createUniqueKey(PlayerInventories.PLAYER_INVENTORIES, "KEY_player_inventories_PRIMARY", PlayerInventories.PLAYER_INVENTORIES.ID);
        public static final UniqueKey<SafetimeRecord> KEY_SAFETIME_PRIMARY = Internal.createUniqueKey(Safetime.SAFETIME, "KEY_safetime_PRIMARY", Safetime.SAFETIME.ID);
        public static final UniqueKey<SafetimeRecord> KEY_SAFETIME_USER_ID = Internal.createUniqueKey(Safetime.SAFETIME, "KEY_safetime_user_id", Safetime.SAFETIME.USER_ID);
        public static final UniqueKey<SimplecmdsLocationsRecord> KEY_SIMPLECMDS_LOCATIONS_PRIMARY = Internal.createUniqueKey(SimplecmdsLocations.SIMPLECMDS_LOCATIONS, "KEY_simplecmds_locations_PRIMARY", SimplecmdsLocations.SIMPLECMDS_LOCATIONS.ID);
        public static final UniqueKey<SimplecmdsLocationsRecord> KEY_SIMPLECMDS_LOCATIONS_NAME = Internal.createUniqueKey(SimplecmdsLocations.SIMPLECMDS_LOCATIONS, "KEY_simplecmds_locations_name", SimplecmdsLocations.SIMPLECMDS_LOCATIONS.NAME);
        public static final UniqueKey<SimplecmdsWarpsRecord> KEY_SIMPLECMDS_WARPS_PRIMARY = Internal.createUniqueKey(SimplecmdsWarps.SIMPLECMDS_WARPS, "KEY_simplecmds_warps_PRIMARY", SimplecmdsWarps.SIMPLECMDS_WARPS.ID);
        public static final UniqueKey<SimplecmdsWarpsRecord> KEY_SIMPLECMDS_WARPS_NAME = Internal.createUniqueKey(SimplecmdsWarps.SIMPLECMDS_WARPS, "KEY_simplecmds_warps_name", SimplecmdsWarps.SIMPLECMDS_WARPS.NAME);
        public static final UniqueKey<TerrenosRecord> KEY_TERRENOS_PRIMARY = Internal.createUniqueKey(Terrenos.TERRENOS, "KEY_terrenos_PRIMARY", Terrenos.TERRENOS.ID);
        public static final UniqueKey<TerrenosPlayersRecord> KEY_TERRENOS_PLAYERS_PRIMARY = Internal.createUniqueKey(TerrenosPlayers.TERRENOS_PLAYERS, "KEY_terrenos_players_PRIMARY", TerrenosPlayers.TERRENOS_PLAYERS.ID);
        public static final UniqueKey<TerrenosPlayersRecord> KEY_TERRENOS_PLAYERS_TERRENO_ENTRY = Internal.createUniqueKey(TerrenosPlayers.TERRENOS_PLAYERS, "KEY_terrenos_players_terreno_entry", TerrenosPlayers.TERRENOS_PLAYERS.TERRENO_ID, TerrenosPlayers.TERRENOS_PLAYERS.TYPE, TerrenosPlayers.TERRENOS_PLAYERS.PLAYER_ID);
        public static final UniqueKey<WhitelistcmdRecord> KEY_WHITELISTCMD_PRIMARY = Internal.createUniqueKey(Whitelistcmd.WHITELISTCMD, "KEY_whitelistcmd_PRIMARY", Whitelistcmd.WHITELISTCMD.COMMAND);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<FloatshopItemsRecord, FloatshopShopsRecord> FLOATSHOP_ITEMS_IBFK_1 = Internal.createForeignKey(Keys.KEY_FLOATSHOP_SHOPS_PRIMARY, FloatshopItems.FLOATSHOP_ITEMS, "floatshop_items_ibfk_1", FloatshopItems.FLOATSHOP_ITEMS.SHOP_ID);
    }
}