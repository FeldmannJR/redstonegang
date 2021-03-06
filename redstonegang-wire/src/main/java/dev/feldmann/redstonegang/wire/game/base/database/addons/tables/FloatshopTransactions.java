/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.database.addons.tables;


import dev.feldmann.redstonegang.wire.game.base.database.addons.DefaultSchema;
import dev.feldmann.redstonegang.wire.game.base.database.addons.Indexes;
import dev.feldmann.redstonegang.wire.game.base.database.addons.Keys;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.FloatshopTransactionsRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FloatshopTransactions extends TableImpl<FloatshopTransactionsRecord> {

    private static final long serialVersionUID = 1387313435;

    /**
     * The reference instance of <code>floatshop_transactions</code>
     */
    public static final FloatshopTransactions FLOATSHOP_TRANSACTIONS = new FloatshopTransactions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FloatshopTransactionsRecord> getRecordType() {
        return FloatshopTransactionsRecord.class;
    }

    /**
     * The column <code>floatshop_transactions.id</code>.
     */
    public final TableField<FloatshopTransactionsRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>floatshop_transactions.item_id</code>.
     */
    public final TableField<FloatshopTransactionsRecord, Integer> ITEM_ID = createField("item_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>floatshop_transactions.date_min</code>.
     */
    public final TableField<FloatshopTransactionsRecord, Timestamp> DATE_MIN = createField("date_min", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>floatshop_transactions.date_max</code>.
     */
    public final TableField<FloatshopTransactionsRecord, Timestamp> DATE_MAX = createField("date_max", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>floatshop_transactions.quantity</code>.
     */
    public final TableField<FloatshopTransactionsRecord, Integer> QUANTITY = createField("quantity", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>floatshop_transactions</code> table reference
     */
    public FloatshopTransactions() {
        this(DSL.name("floatshop_transactions"), null);
    }

    /**
     * Create an aliased <code>floatshop_transactions</code> table reference
     */
    public FloatshopTransactions(String alias) {
        this(DSL.name(alias), FLOATSHOP_TRANSACTIONS);
    }

    /**
     * Create an aliased <code>floatshop_transactions</code> table reference
     */
    public FloatshopTransactions(Name alias) {
        this(alias, FLOATSHOP_TRANSACTIONS);
    }

    private FloatshopTransactions(Name alias, Table<FloatshopTransactionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private FloatshopTransactions(Name alias, Table<FloatshopTransactionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> FloatshopTransactions(Table<O> child, ForeignKey<O, FloatshopTransactionsRecord> key) {
        super(child, key, FLOATSHOP_TRANSACTIONS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.FLOATSHOP_TRANSACTIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<FloatshopTransactionsRecord, Long> getIdentity() {
        return Keys.IDENTITY_FLOATSHOP_TRANSACTIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<FloatshopTransactionsRecord> getPrimaryKey() {
        return Keys.KEY_FLOATSHOP_TRANSACTIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<FloatshopTransactionsRecord>> getKeys() {
        return Arrays.<UniqueKey<FloatshopTransactionsRecord>>asList(Keys.KEY_FLOATSHOP_TRANSACTIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatshopTransactions as(String alias) {
        return new FloatshopTransactions(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatshopTransactions as(Name alias) {
        return new FloatshopTransactions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public FloatshopTransactions rename(String name) {
        return new FloatshopTransactions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public FloatshopTransactions rename(Name name) {
        return new FloatshopTransactions(name, null);
    }
}
