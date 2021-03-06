/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables;


import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Indexes;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Keys;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.RedstonegangCommon;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PermissionsRecord;

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
public class Permissions extends TableImpl<PermissionsRecord> {

    private static final long serialVersionUID = 1662549584;

    /**
     * The reference instance of <code>redstonegang_common.permissions</code>
     */
    public static final Permissions PERMISSIONS = new Permissions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PermissionsRecord> getRecordType() {
        return PermissionsRecord.class;
    }

    /**
     * The column <code>redstonegang_common.permissions.id</code>.
     */
    public final TableField<PermissionsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>redstonegang_common.permissions.key</code>.
     */
    public final TableField<PermissionsRecord, String> KEY = createField("key", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.permissions.value</code>.
     */
    public final TableField<PermissionsRecord, Short> VALUE = createField("value", org.jooq.impl.SQLDataType.SMALLINT.nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.permissions.owner</code>.
     */
    public final TableField<PermissionsRecord, Integer> OWNER = createField("owner", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.permissions.type</code>.
     */
    public final TableField<PermissionsRecord, Boolean> TYPE = createField("type", org.jooq.impl.SQLDataType.BIT.nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.permissions.server</code>.
     */
    public final TableField<PermissionsRecord, Integer> SERVER = createField("server", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * Create a <code>redstonegang_common.permissions</code> table reference
     */
    public Permissions() {
        this(DSL.name("permissions"), null);
    }

    /**
     * Create an aliased <code>redstonegang_common.permissions</code> table reference
     */
    public Permissions(String alias) {
        this(DSL.name(alias), PERMISSIONS);
    }

    /**
     * Create an aliased <code>redstonegang_common.permissions</code> table reference
     */
    public Permissions(Name alias) {
        this(alias, PERMISSIONS);
    }

    private Permissions(Name alias, Table<PermissionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Permissions(Name alias, Table<PermissionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Permissions(Table<O> child, ForeignKey<O, PermissionsRecord> key) {
        super(child, key, PERMISSIONS);
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
        return Arrays.<Index>asList(Indexes.PERMISSIONS_PERMISSION, Indexes.PERMISSIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<PermissionsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_PERMISSIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PermissionsRecord> getPrimaryKey() {
        return Keys.KEY_PERMISSIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PermissionsRecord>> getKeys() {
        return Arrays.<UniqueKey<PermissionsRecord>>asList(Keys.KEY_PERMISSIONS_PRIMARY, Keys.KEY_PERMISSIONS_PERMISSION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Permissions as(String alias) {
        return new Permissions(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Permissions as(Name alias) {
        return new Permissions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Permissions rename(String name) {
        return new Permissions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Permissions rename(Name name) {
        return new Permissions(name, null);
    }
}
