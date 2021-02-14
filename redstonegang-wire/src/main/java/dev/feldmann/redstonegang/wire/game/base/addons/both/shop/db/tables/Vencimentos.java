/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.tables;


import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.DefaultSchema;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.Indexes;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.Keys;
import dev.feldmann.redstonegang.wire.game.base.addons.both.shop.db.tables.records.VencimentosRecord;

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
public class Vencimentos extends TableImpl<VencimentosRecord> {

    private static final long serialVersionUID = -1590340732;

    /**
     * The reference redstonegang of <code>vencimentos</code>
     */
    public static final Vencimentos VENCIMENTOS = new Vencimentos();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VencimentosRecord> getRecordType() {
        return VencimentosRecord.class;
    }

    /**
     * The column <code>vencimentos.id</code>.
     */
    public final TableField<VencimentosRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>vencimentos.player</code>.
     */
    public final TableField<VencimentosRecord, Integer> PLAYER = createField("player", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>vencimentos.grupo</code>.
     */
    public final TableField<VencimentosRecord, Integer> GRUPO = createField("grupo", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>vencimentos.start</code>.
     */
    public final TableField<VencimentosRecord, Timestamp> START = createField("start", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>vencimentos.dias</code>.
     */
    public final TableField<VencimentosRecord, Integer> DIAS = createField("dias", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * Create a <code>vencimentos</code> table reference
     */
    public Vencimentos() {
        this(DSL.name("vencimentos"), null);
    }

    /**
     * Create an aliased <code>vencimentos</code> table reference
     */
    public Vencimentos(String alias) {
        this(DSL.name(alias), VENCIMENTOS);
    }

    /**
     * Create an aliased <code>vencimentos</code> table reference
     */
    public Vencimentos(Name alias) {
        this(alias, VENCIMENTOS);
    }

    private Vencimentos(Name alias, Table<VencimentosRecord> aliased) {
        this(alias, aliased, null);
    }

    private Vencimentos(Name alias, Table<VencimentosRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Vencimentos(Table<O> child, ForeignKey<O, VencimentosRecord> key) {
        super(child, key, VENCIMENTOS);
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
        return Arrays.<Index>asList(Indexes.VENCIMENTOS_PLAYER, Indexes.VENCIMENTOS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<VencimentosRecord, Integer> getIdentity() {
        return Keys.IDENTITY_VENCIMENTOS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<VencimentosRecord> getPrimaryKey() {
        return Keys.KEY_VENCIMENTOS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VencimentosRecord>> getKeys() {
        return Arrays.<UniqueKey<VencimentosRecord>>asList(Keys.KEY_VENCIMENTOS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vencimentos as(String alias) {
        return new Vencimentos(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vencimentos as(Name alias) {
        return new Vencimentos(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Vencimentos rename(String name) {
        return new Vencimentos(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Vencimentos rename(Name name) {
        return new Vencimentos(name, null);
    }
}