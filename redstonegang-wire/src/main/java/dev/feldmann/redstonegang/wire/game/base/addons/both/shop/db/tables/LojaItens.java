/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.tables;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.DefaultSchema;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.Indexes;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.Keys;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.tables.records.LojaItensRecord;

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
public class LojaItens extends TableImpl<LojaItensRecord> {

    private static final long serialVersionUID = 728386080;

    /**
     * The reference redstonegang of <code>loja_itens</code>
     */
    public static final LojaItens LOJA_ITENS = new LojaItens();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LojaItensRecord> getRecordType() {
        return LojaItensRecord.class;
    }

    /**
     * The column <code>loja_itens.id</code>.
     */
    public final TableField<LojaItensRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>loja_itens.slot</code>.
     */
    public final TableField<LojaItensRecord, Integer> SLOT = createField("slot", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>loja_itens.tipo</code>.
     */
    public final TableField<LojaItensRecord, String> TIPO = createField("tipo", org.jooq.impl.SQLDataType.VARCHAR(60), this, "");

    /**
     * The column <code>loja_itens.pageid</code>.
     */
    public final TableField<LojaItensRecord, Integer> PAGEID = createField("pageid", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>loja_itens.vars</code>.
     */
    public final TableField<LojaItensRecord, String> VARS = createField("vars", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>loja_itens</code> table reference
     */
    public LojaItens() {
        this(DSL.name("loja_itens"), null);
    }

    /**
     * Create an aliased <code>loja_itens</code> table reference
     */
    public LojaItens(String alias) {
        this(DSL.name(alias), LOJA_ITENS);
    }

    /**
     * Create an aliased <code>loja_itens</code> table reference
     */
    public LojaItens(Name alias) {
        this(alias, LOJA_ITENS);
    }

    private LojaItens(Name alias, Table<LojaItensRecord> aliased) {
        this(alias, aliased, null);
    }

    private LojaItens(Name alias, Table<LojaItensRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> LojaItens(Table<O> child, ForeignKey<O, LojaItensRecord> key) {
        super(child, key, LOJA_ITENS);
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
        return Arrays.<Index>asList(Indexes.LOJA_ITENS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<LojaItensRecord, Integer> getIdentity() {
        return Keys.IDENTITY_LOJA_ITENS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<LojaItensRecord> getPrimaryKey() {
        return Keys.KEY_LOJA_ITENS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<LojaItensRecord>> getKeys() {
        return Arrays.<UniqueKey<LojaItensRecord>>asList(Keys.KEY_LOJA_ITENS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LojaItens as(String alias) {
        return new LojaItens(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LojaItens as(Name alias) {
        return new LojaItens(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public LojaItens rename(String name) {
        return new LojaItens(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public LojaItens rename(Name name) {
        return new LojaItens(name, null);
    }
}