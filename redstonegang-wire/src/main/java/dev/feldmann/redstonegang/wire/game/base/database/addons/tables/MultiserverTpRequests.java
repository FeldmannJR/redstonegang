/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.database.addons.tables;


import dev.feldmann.redstonegang.wire.game.base.database.addons.DefaultSchema;
import dev.feldmann.redstonegang.wire.game.base.database.addons.Indexes;
import dev.feldmann.redstonegang.wire.game.base.database.addons.Keys;
import dev.feldmann.redstonegang.wire.game.base.database.addons.tables.records.MultiserverTpRequestsRecord;

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
public class MultiserverTpRequests extends TableImpl<MultiserverTpRequestsRecord> {

    private static final long serialVersionUID = 1587931294;

    /**
     * The reference instance of <code>multiserver_tp_requests</code>
     */
    public static final MultiserverTpRequests MULTISERVER_TP_REQUESTS = new MultiserverTpRequests();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MultiserverTpRequestsRecord> getRecordType() {
        return MultiserverTpRequestsRecord.class;
    }

    /**
     * The column <code>multiserver_tp_requests.id</code>.
     */
    public final TableField<MultiserverTpRequestsRecord, Long> ID = createField("id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).identity(true), this, "");

    /**
     * The column <code>multiserver_tp_requests.requester</code>.
     */
    public final TableField<MultiserverTpRequestsRecord, Integer> REQUESTER = createField("requester", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>multiserver_tp_requests.requested</code>.
     */
    public final TableField<MultiserverTpRequestsRecord, Integer> REQUESTED = createField("requested", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>multiserver_tp_requests.request_time</code>.
     */
    public final TableField<MultiserverTpRequestsRecord, Timestamp> REQUEST_TIME = createField("request_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>multiserver_tp_requests.request_expire</code>.
     */
    public final TableField<MultiserverTpRequestsRecord, Timestamp> REQUEST_EXPIRE = createField("request_expire", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>multiserver_tp_requests</code> table reference
     */
    public MultiserverTpRequests() {
        this(DSL.name("multiserver_tp_requests"), null);
    }

    /**
     * Create an aliased <code>multiserver_tp_requests</code> table reference
     */
    public MultiserverTpRequests(String alias) {
        this(DSL.name(alias), MULTISERVER_TP_REQUESTS);
    }

    /**
     * Create an aliased <code>multiserver_tp_requests</code> table reference
     */
    public MultiserverTpRequests(Name alias) {
        this(alias, MULTISERVER_TP_REQUESTS);
    }

    private MultiserverTpRequests(Name alias, Table<MultiserverTpRequestsRecord> aliased) {
        this(alias, aliased, null);
    }

    private MultiserverTpRequests(Name alias, Table<MultiserverTpRequestsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> MultiserverTpRequests(Table<O> child, ForeignKey<O, MultiserverTpRequestsRecord> key) {
        super(child, key, MULTISERVER_TP_REQUESTS);
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
        return Arrays.<Index>asList(Indexes.MULTISERVER_TP_REQUESTS_PRIMARY, Indexes.MULTISERVER_TP_REQUESTS_REQUESTED_FK, Indexes.MULTISERVER_TP_REQUESTS_REQUEST_UNIQUE_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MultiserverTpRequestsRecord, Long> getIdentity() {
        return Keys.IDENTITY_MULTISERVER_TP_REQUESTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MultiserverTpRequestsRecord> getPrimaryKey() {
        return Keys.KEY_MULTISERVER_TP_REQUESTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MultiserverTpRequestsRecord>> getKeys() {
        return Arrays.<UniqueKey<MultiserverTpRequestsRecord>>asList(Keys.KEY_MULTISERVER_TP_REQUESTS_PRIMARY, Keys.KEY_MULTISERVER_TP_REQUESTS_REQUEST_UNIQUE_FK);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiserverTpRequests as(String alias) {
        return new MultiserverTpRequests(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MultiserverTpRequests as(Name alias) {
        return new MultiserverTpRequests(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MultiserverTpRequests rename(String name) {
        return new MultiserverTpRequests(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MultiserverTpRequests rename(Name name) {
        return new MultiserverTpRequests(name, null);
    }
}
