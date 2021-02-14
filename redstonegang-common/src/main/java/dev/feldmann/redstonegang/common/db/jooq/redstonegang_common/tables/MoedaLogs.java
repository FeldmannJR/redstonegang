/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables;


import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Indexes;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Keys;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.RedstonegangCommon;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.MoedaLogsRecord;

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
public class MoedaLogs extends TableImpl<MoedaLogsRecord> {

    private static final long serialVersionUID = 891365552;

    /**
     * The reference instance of <code>redstonegang_common.moeda_logs</code>
     */
    public static final MoedaLogs MOEDA_LOGS = new MoedaLogs();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MoedaLogsRecord> getRecordType() {
        return MoedaLogsRecord.class;
    }

    /**
     * The column <code>redstonegang_common.moeda_logs.id</code>.
     */
    public final TableField<MoedaLogsRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>redstonegang_common.moeda_logs.playerId</code>.
     */
    public final TableField<MoedaLogsRecord, Integer> PLAYERID = createField("playerId", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>redstonegang_common.moeda_logs.value</code>.
     */
    public final TableField<MoedaLogsRecord, Integer> VALUE = createField("value", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>redstonegang_common.moeda_logs.moeda</code>.
     */
    public final TableField<MoedaLogsRecord, String> MOEDA = createField("moeda", org.jooq.impl.SQLDataType.VARCHAR(255).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>redstonegang_common.moeda_logs.server</code>.
     */
    public final TableField<MoedaLogsRecord, String> SERVER = createField("server", org.jooq.impl.SQLDataType.VARCHAR(255).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>redstonegang_common.moeda_logs.desc</code>.
     */
    public final TableField<MoedaLogsRecord, String> DESC = createField("desc", org.jooq.impl.SQLDataType.VARCHAR(255).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>redstonegang_common.moeda_logs.when</code>.
     */
    public final TableField<MoedaLogsRecord, Timestamp> WHEN = createField("when", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>redstonegang_common.moeda_logs</code> table reference
     */
    public MoedaLogs() {
        this(DSL.name("moeda_logs"), null);
    }

    /**
     * Create an aliased <code>redstonegang_common.moeda_logs</code> table reference
     */
    public MoedaLogs(String alias) {
        this(DSL.name(alias), MOEDA_LOGS);
    }

    /**
     * Create an aliased <code>redstonegang_common.moeda_logs</code> table reference
     */
    public MoedaLogs(Name alias) {
        this(alias, MOEDA_LOGS);
    }

    private MoedaLogs(Name alias, Table<MoedaLogsRecord> aliased) {
        this(alias, aliased, null);
    }

    private MoedaLogs(Name alias, Table<MoedaLogsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MoedaLogs(Table<O> child, ForeignKey<O, MoedaLogsRecord> key) {
        super(child, key, MOEDA_LOGS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return RedstonegangCommon.REDSTONEGANG_COMMON;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MOEDA_LOGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MoedaLogsRecord, Long> getIdentity() {
        return Keys.IDENTITY_MOEDA_LOGS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MoedaLogsRecord> getPrimaryKey() {
        return Keys.KEY_MOEDA_LOGS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MoedaLogsRecord>> getKeys() {
        return Arrays.<UniqueKey<MoedaLogsRecord>>asList(Keys.KEY_MOEDA_LOGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoedaLogs as(String alias) {
        return new MoedaLogs(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MoedaLogs as(Name alias) {
        return new MoedaLogs(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MoedaLogs rename(String name) {
        return new MoedaLogs(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MoedaLogs rename(Name name) {
        return new MoedaLogs(name, null);
    }
}