package dev.feldmann.redstonegang.wire.game.base.addons.server.floatshop;

import dev.feldmann.redstonegang.common.db.Database;

import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.FloatshopItemsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.FloatshopShopsRecord;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.FloatshopTransactionsRecord;
import dev.feldmann.redstonegang.wire.utils.ItemSerializer;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static dev.feldmann.redstonegang.wire.game.base.database.addons.Tables.*;

public class FloatShopDB extends Database {

    private static final String DB_PREFIX = "floatshop";

    private FloatShopAddon addon;

    public FloatShopDB(FloatShopAddon addon, String database) {
        super(database);
        this.addon = addon;
    }

    @Override
    public void createTables() {
        Table TRANSACTIONS = DSL.table(DSL.name(DB_PREFIX + "_transactions"));
        Field<Long> TRANSACTION_ID = DSL.field(DSL.name("id"), SQLDataType.BIGINT.nullable(false).identity(true));
        Field<Integer> TRANSACTION_ITEM_ID = DSL.field(DSL.name("item_id"), SQLDataType.INTEGER.nullable(false));
        Field<Boolean> TRANSACTION_TYPE = DSL.field(DSL.name("type"), SQLDataType.BOOLEAN);
        Field<Timestamp> TRANSACTION_DATE_MIN = DSL.field(DSL.name("date_min"), SQLDataType.TIMESTAMP);
        Field<Timestamp> TRANSACTION_DATE_MAX = DSL.field(DSL.name("date_max"), SQLDataType.TIMESTAMP);

        Field<Integer> TRANSACTION_QUANTITY = DSL.field(DSL.name("quantity"), SQLDataType.INTEGER.nullable(false));

        Table SHOPS = DSL.table(DSL.name(DB_PREFIX + "_shops"));
        Field<Integer> SHOP_ID = DSL.field(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true));
        Field<String> SHOP_NPC_UUID = DSL.field(DSL.name("npc_uuid"), SQLDataType.VARCHAR(36));
        Field<String> SHOP_PERMISSION = DSL.field(DSL.name("permission"), SQLDataType.VARCHAR(60));

        Table ITEMS = DSL.table(DSL.name(DB_PREFIX + "_items"));
        Field<Integer> ITEM_ID = DSL.field(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true));
        Field<Integer> ITEM_SHOP_ID = DSL.field(DSL.name("shop_id"), SQLDataType.INTEGER);
        Field<Integer> ITEM_SLOT = DSL.field("slot", SQLDataType.INTEGER.nullable(true));
        Field<byte[]> ITEM_ITEMSTACK = DSL.field(DSL.name("itemstack"), SQLDataType.BLOB);
        Field<Double> ITEM_SELL_PRICE = DSL.field(DSL.name("sell_price"), SQLDataType.DOUBLE);
        Field<Double> ITEM_BUY_PRICE = DSL.field(DSL.name("buy_price"), SQLDataType.DOUBLE);
        Field<Integer> ITEM_MAX_DIFFERENCE = DSL.field(DSL.name("max_difference"), SQLDataType.INTEGER);
        Field<Double> ITEM_PERCENTAGE_HIGH = DSL.field(DSL.name("percentage_high"), SQLDataType.DOUBLE);
        Field<Double> ITEM_PERCENTAGE_LOW = DSL.field(DSL.name("percentage_low"), SQLDataType.DOUBLE);

        dsl().createTableIfNotExists(SHOPS)
                .columns(SHOP_ID, SHOP_NPC_UUID, SHOP_PERMISSION, DSL.field("linhas", SQLDataType.INTEGER.defaultValue(3)))
                .constraint(DSL.unique(SHOP_NPC_UUID))
                .constraint(DSL.primaryKey(SHOP_ID))
                .execute();

        dsl().createTableIfNotExists(ITEMS)
                .columns(ITEM_ID, ITEM_SHOP_ID, ITEM_ITEMSTACK, ITEM_SELL_PRICE, ITEM_MAX_DIFFERENCE, ITEM_BUY_PRICE, ITEM_PERCENTAGE_HIGH, ITEM_PERCENTAGE_LOW, ITEM_SLOT)
                .column(DSL.field("available", SQLDataType.DOUBLE))
                .column(DSL.field("regen_minute", SQLDataType.DOUBLE))
                .column(DSL.field("max_available", SQLDataType.INTEGER))
                .constraint(DSL.foreignKey(ITEM_SHOP_ID).references(SHOPS, SHOP_ID).onDeleteCascade())
                .constraint(DSL.primaryKey(ITEM_ID))
                .execute();

        dsl().createTableIfNotExists(TRANSACTIONS)
                .columns(TRANSACTION_ID, TRANSACTION_ITEM_ID, TRANSACTION_DATE_MIN, TRANSACTION_DATE_MAX, TRANSACTION_QUANTITY)
                .constraint(DSL.primaryKey(TRANSACTION_ID))
                .execute();
    }

    public void saveShop(FloatShop shop) {
        if (shop.isSaved()) {
            dsl().update(FLOATSHOP_SHOPS)
                    .set(FLOATSHOP_SHOPS.NPC_UUID, shop.getNpcUUID().toString())
                    .set(FLOATSHOP_SHOPS.PERMISSION, shop.getPermission())
                    .set(FLOATSHOP_SHOPS.LINHAS, shop.getLinhas())
                    .where(FLOATSHOP_SHOPS.ID.eq(shop.getId()))
                    .execute();

        } else {
            FloatshopShopsRecord r = dsl().insertInto(FLOATSHOP_SHOPS)
                    .columns(FLOATSHOP_SHOPS.NPC_UUID, FLOATSHOP_SHOPS.PERMISSION, FLOATSHOP_SHOPS.LINHAS)
                    .values(shop.getNpcUUID().toString(), shop.getPermission(), shop.getLinhas())
                    .returning(FLOATSHOP_SHOPS.ID).fetchOne();
            shop.setId(r.getId());
        }
    }

    public void deleteShop(FloatShop shop) {
        if (shop.isSaved()) {
            dsl().deleteFrom(FLOATSHOP_ITEMS).where(FLOATSHOP_ITEMS.SHOP_ID.eq(shop.getId())).execute();
            dsl().deleteFrom(FLOATSHOP_SHOPS).where(FLOATSHOP_SHOPS.ID.eq(shop.getId())).execute();
        }
    }

    public void saveItem(FloatItem item) {
        HashMap<Field, Object> vals = new HashMap<>();
        vals.put(FLOATSHOP_ITEMS.ITEMSTACK, ItemSerializer.serializeItemStack(item.item));
        vals.put(FLOATSHOP_ITEMS.BUY_PRICE, item.buyPrice);
        vals.put(FLOATSHOP_ITEMS.SELL_PRICE, item.sellPrice);
        vals.put(FLOATSHOP_ITEMS.PERCENTAGE_HIGH, item.highPercentage);
        vals.put(FLOATSHOP_ITEMS.PERCENTAGE_LOW, item.lowPercentage);
        vals.put(FLOATSHOP_ITEMS.SHOP_ID, item.shopId);
        vals.put(FLOATSHOP_ITEMS.AVAILABLE, item.available);
        vals.put(FLOATSHOP_ITEMS.MAX_AVAILABLE, item.maxAvailable);
        vals.put(FLOATSHOP_ITEMS.REGEN_MINUTE, item.perMinuteRegen);
        vals.put(FLOATSHOP_ITEMS.SLOT, item.slot);
        vals.put(FLOATSHOP_ITEMS.MAX_DIFFERENCE, item.maxDifference);
        if (!item.isSaved()) {
            Record r = dsl().insertInto(FLOATSHOP_ITEMS)
                    .set(vals)
                    .returning(FLOATSHOP_ITEMS.ID)
                    .fetchOne();
            item.id = r.get(FLOATSHOP_ITEMS.ID);

        } else {
            dsl().update(FLOATSHOP_ITEMS).set(vals).where(FLOATSHOP_ITEMS.ID.eq(item.id)).execute();
        }
    }

    public void deleteItem(FloatItem item) {
        if (item.isSaved()) {
            dsl().deleteFrom(FLOATSHOP_ITEMS).where(FLOATSHOP_ITEMS.ID.eq(item.id)).execute();
        }
    }

    public void saveTransaction(ItemTransaction trans) {
        dsl().update(FLOATSHOP_TRANSACTIONS).set(FLOATSHOP_TRANSACTIONS.QUANTITY, trans.quantity).where(FLOATSHOP_TRANSACTIONS.ID.eq(trans.id)).execute();
    }

    public void addTransaction(ItemTransaction trans) {
        trans.id = dsl().insertInto(FLOATSHOP_TRANSACTIONS)
                .columns(FLOATSHOP_TRANSACTIONS.ITEM_ID, FLOATSHOP_TRANSACTIONS.QUANTITY, FLOATSHOP_TRANSACTIONS.DATE_MIN, FLOATSHOP_TRANSACTIONS.DATE_MAX)
                .values(trans.itemId, trans.quantity, trans.min, trans.max)
                .returning(FLOATSHOP_TRANSACTIONS.ID)
                .fetchOne().getId();
    }


    public List<ItemTransaction> loadTransactions() {
        List<ItemTransaction> transactions = new ArrayList<>();
        Result<FloatshopTransactionsRecord> fetch = dsl().selectFrom(FLOATSHOP_TRANSACTIONS).where(FLOATSHOP_TRANSACTIONS.DATE_MAX.greaterOrEqual(DSL.timestampSub(DSL.now(), FloatShopAddon.horasDemanda, DatePart.HOUR))).fetch();
        for (FloatshopTransactionsRecord f : fetch) {
            ItemTransaction trans = new ItemTransaction(f.getItemId(), f.getQuantity(), f.getDateMin(), f.getDateMax());
            trans.id = f.getId();
            transactions.add(trans);
        }
        return transactions;
    }

    public List<FloatShop> loadShops() {
        List<FloatShop> shops = new ArrayList<>();
        List<FloatItem> items = loadItems();

        Result<FloatshopShopsRecord> rs = dsl().selectFrom(FLOATSHOP_SHOPS).fetch();
        for (FloatshopShopsRecord r : rs) {
            FloatShop shop = new FloatShop(r.getId(), UUID.fromString(r.getNpcUuid()), r.getPermission(), r.getLinhas());
            shop.setItens(items.stream().filter(i -> i.shopId == r.getId()).collect(Collectors.toList()));
            shops.add(shop);
        }
        return shops;
    }


    public List<FloatItem> loadItems() {
        List<FloatItem> items = new ArrayList<>();
        List<ItemTransaction> transactions = loadTransactions();
        Result<FloatshopItemsRecord> rs = dsl().selectFrom(FLOATSHOP_ITEMS).fetch();
        for (FloatshopItemsRecord r : rs) {
            FloatItem vi = new FloatItem(addon, r.getId());
            vi.item = ItemSerializer.deserializeItemStack(r.getItemstack());
            vi.buyPrice = r.getBuyPrice();
            vi.sellPrice = r.getSellPrice();
            vi.highPercentage = r.getPercentageHigh();
            vi.lowPercentage = r.getPercentageLow();
            vi.maxDifference = r.getMaxDifference();
            vi.shopId = r.getShopId();
            vi.maxAvailable = r.getMaxAvailable();
            vi.perMinuteRegen = r.getRegenMinute();
            vi.available = r.getAvailable();
            vi.slot = r.getSlot();
            vi.transactions.transactions = transactions.stream().filter(t -> t.itemId == r.getId()).collect(Collectors.toList());
            items.add(vi);
        }
        return items;
    }


    public void setAvailable(int id, double available) {
        dsl().update(FLOATSHOP_ITEMS).set(FLOATSHOP_ITEMS.AVAILABLE, available).where(FLOATSHOP_ITEMS.ID.eq(id)).executeAsync();
    }
}
