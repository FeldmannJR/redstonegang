/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addons.server.correio.db.tables;


import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.db.DefaultSchema;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.db.Indexes;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.db.Keys;
import dev.feldmann.redstonegang.wire.game.base.addons.server.correio.db.tables.records.CorreioRecord;

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
public class Correio extends TableImpl<CorreioRecord> {

    private static final long serialVersionUID = 1398564986;

    /**
     * The reference redstonegang of <code>correio</code>
     */
    public static final Correio CORREIO = new Correio();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CorreioRecord> getRecordType() {
        return CorreioRecord.class;
    }

    /**
     * The column <code>correio.id</code>.
     */
    public final TableField<CorreioRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>correio.remetente</code>.
     */
    public final TableField<CorreioRecord, Integer> REMETENTE = createField("remetente", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>correio.destinatario</code>.
     */
    public final TableField<CorreioRecord, Integer> DESTINATARIO = createField("destinatario", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>correio.mensagem</code>.
     */
    public final TableField<CorreioRecord, String> MENSAGEM = createField("mensagem", org.jooq.impl.SQLDataType.VARCHAR(300).nullable(false), this, "");

    /**
     * The column <code>correio.coins</code>.
     */
    public final TableField<CorreioRecord, Double> COINS = createField("coins", org.jooq.impl.SQLDataType.DOUBLE.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.DOUBLE)), this, "");

    /**
     * The column <code>correio.itens</code>.
     */
    public final TableField<CorreioRecord, byte[]> ITENS = createField("itens", org.jooq.impl.SQLDataType.BLOB.nullable(false), this, "");

    /**
     * The column <code>correio.itenstrans</code>.
     */
    public final TableField<CorreioRecord, Boolean> ITENSTRANS = createField("itenstrans", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>correio.aberta</code>.
     */
    public final TableField<CorreioRecord, Boolean> ABERTA = createField("aberta", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BOOLEAN)), this, "");

    /**
     * The column <code>correio.data</code>.
     */
    public final TableField<CorreioRecord, Timestamp> DATA = createField("data", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>correio</code> table reference
     */
    public Correio() {
        this(DSL.name("correio"), null);
    }

    /**
     * Create an aliased <code>correio</code> table reference
     */
    public Correio(String alias) {
        this(DSL.name(alias), CORREIO);
    }

    /**
     * Create an aliased <code>correio</code> table reference
     */
    public Correio(Name alias) {
        this(alias, CORREIO);
    }

    private Correio(Name alias, Table<CorreioRecord> aliased) {
        this(alias, aliased, null);
    }

    private Correio(Name alias, Table<CorreioRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Correio(Table<O> child, ForeignKey<O, CorreioRecord> key) {
        super(child, key, CORREIO);
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
        return Arrays.<Index>asList(Indexes.CORREIO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<CorreioRecord, Long> getIdentity() {
        return Keys.IDENTITY_CORREIO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<CorreioRecord> getPrimaryKey() {
        return Keys.KEY_CORREIO_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<CorreioRecord>> getKeys() {
        return Arrays.<UniqueKey<CorreioRecord>>asList(Keys.KEY_CORREIO_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Correio as(String alias) {
        return new Correio(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Correio as(Name alias) {
        return new Correio(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Correio rename(String name) {
        return new Correio(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Correio rename(Name name) {
        return new Correio(name, null);
    }
}
