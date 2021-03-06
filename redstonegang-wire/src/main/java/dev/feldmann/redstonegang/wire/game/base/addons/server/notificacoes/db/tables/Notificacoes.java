/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.db.tables;


import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.db.Indexes;
import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.db.Keys;
import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.db.RedstonegangSurvival;
import dev.feldmann.redstonegang.wire.game.base.addons.server.notificacoes.db.tables.records.NotificacoesRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class Notificacoes extends TableImpl<NotificacoesRecord> {

    private static final long serialVersionUID = 108351387;

    /**
     * The reference redstonegang of <code>redstonegang_survival.notificacoes</code>
     */
    public static final Notificacoes NOTIFICACOES = new Notificacoes();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<NotificacoesRecord> getRecordType() {
        return NotificacoesRecord.class;
    }

    /**
     * The column <code>redstonegang_survival.notificacoes.type</code>.
     */
    public final TableField<NotificacoesRecord, String> TYPE = createField("type", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>redstonegang_survival.notificacoes.owner</code>.
     */
    public final TableField<NotificacoesRecord, Integer> OWNER = createField("owner", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>redstonegang_survival.notificacoes.vars</code>.
     */
    public final TableField<NotificacoesRecord, String> VARS = createField("vars", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * Create a <code>redstonegang_survival.notificacoes</code> table reference
     */
    public Notificacoes() {
        this(DSL.name("notificacoes"), null);
    }

    /**
     * Create an aliased <code>redstonegang_survival.notificacoes</code> table reference
     */
    public Notificacoes(String alias) {
        this(DSL.name(alias), NOTIFICACOES);
    }

    /**
     * Create an aliased <code>redstonegang_survival.notificacoes</code> table reference
     */
    public Notificacoes(Name alias) {
        this(alias, NOTIFICACOES);
    }

    private Notificacoes(Name alias, Table<NotificacoesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Notificacoes(Name alias, Table<NotificacoesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Notificacoes(Table<O> child, ForeignKey<O, NotificacoesRecord> key) {
        super(child, key, NOTIFICACOES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return RedstonegangSurvival.REDSTONEGANG_SURVIVAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.NOTIFICACOES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<NotificacoesRecord> getPrimaryKey() {
        return Keys.KEY_NOTIFICACOES_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<NotificacoesRecord>> getKeys() {
        return Arrays.<UniqueKey<NotificacoesRecord>>asList(Keys.KEY_NOTIFICACOES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notificacoes as(String alias) {
        return new Notificacoes(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Notificacoes as(Name alias) {
        return new Notificacoes(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Notificacoes rename(String name) {
        return new Notificacoes(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Notificacoes rename(Name name) {
        return new Notificacoes(name, null);
    }
}
