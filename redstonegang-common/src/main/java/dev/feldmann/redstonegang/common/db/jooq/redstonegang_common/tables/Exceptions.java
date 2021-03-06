/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables;


import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Indexes;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Keys;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.RedstonegangCommon;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.ExceptionsRecord;

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
public class Exceptions extends TableImpl<ExceptionsRecord> {

    private static final long serialVersionUID = 2017609907;

    /**
     * The reference instance of <code>redstonegang_common.exceptions</code>
     */
    public static final Exceptions EXCEPTIONS = new Exceptions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ExceptionsRecord> getRecordType() {
        return ExceptionsRecord.class;
    }

    /**
     * The column <code>redstonegang_common.exceptions.id</code>.
     */
    public final TableField<ExceptionsRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>redstonegang_common.exceptions.occurred</code>.
     */
    public final TableField<ExceptionsRecord, Timestamp> OCCURRED = createField("occurred", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("current_timestamp()", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>redstonegang_common.exceptions.server</code>.
     */
    public final TableField<ExceptionsRecord, String> SERVER = createField("server", org.jooq.impl.SQLDataType.VARCHAR(32).nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.exceptions.game</code>.
     */
    public final TableField<ExceptionsRecord, String> GAME = createField("game", org.jooq.impl.SQLDataType.VARCHAR(32).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>redstonegang_common.exceptions.error</code>.
     */
    public final TableField<ExceptionsRecord, String> ERROR = createField("error", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * Create a <code>redstonegang_common.exceptions</code> table reference
     */
    public Exceptions() {
        this(DSL.name("exceptions"), null);
    }

    /**
     * Create an aliased <code>redstonegang_common.exceptions</code> table reference
     */
    public Exceptions(String alias) {
        this(DSL.name(alias), EXCEPTIONS);
    }

    /**
     * Create an aliased <code>redstonegang_common.exceptions</code> table reference
     */
    public Exceptions(Name alias) {
        this(alias, EXCEPTIONS);
    }

    private Exceptions(Name alias, Table<ExceptionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Exceptions(Name alias, Table<ExceptionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Exceptions(Table<O> child, ForeignKey<O, ExceptionsRecord> key) {
        super(child, key, EXCEPTIONS);
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
        return Arrays.<Index>asList(Indexes.EXCEPTIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ExceptionsRecord, Long> getIdentity() {
        return Keys.IDENTITY_EXCEPTIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ExceptionsRecord> getPrimaryKey() {
        return Keys.KEY_EXCEPTIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ExceptionsRecord>> getKeys() {
        return Arrays.<UniqueKey<ExceptionsRecord>>asList(Keys.KEY_EXCEPTIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Exceptions as(String alias) {
        return new Exceptions(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Exceptions as(Name alias) {
        return new Exceptions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Exceptions rename(String name) {
        return new Exceptions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Exceptions rename(Name name) {
        return new Exceptions(name, null);
    }
}
