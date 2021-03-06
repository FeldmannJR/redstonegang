/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables;


import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Indexes;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Keys;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.RedstonegangCommon;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.PermissionsDescRecord;

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
public class PermissionsDesc extends TableImpl<PermissionsDescRecord> {

    private static final long serialVersionUID = 542419346;

    /**
     * The reference instance of <code>redstonegang_common.permissions_desc</code>
     */
    public static final PermissionsDesc PERMISSIONS_DESC = new PermissionsDesc();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PermissionsDescRecord> getRecordType() {
        return PermissionsDescRecord.class;
    }

    /**
     * The column <code>redstonegang_common.permissions_desc.key</code>.
     */
    public final TableField<PermissionsDescRecord, String> KEY = createField("key", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.permissions_desc.type</code>.
     */
    public final TableField<PermissionsDescRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.permissions_desc.name</code>.
     */
    public final TableField<PermissionsDescRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.permissions_desc.desc</code>.
     */
    public final TableField<PermissionsDescRecord, String> DESC = createField("desc", org.jooq.impl.SQLDataType.VARCHAR(250).defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>redstonegang_common.permissions_desc.server</code>.
     */
    public final TableField<PermissionsDescRecord, String> SERVER = createField("server", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * Create a <code>redstonegang_common.permissions_desc</code> table reference
     */
    public PermissionsDesc() {
        this(DSL.name("permissions_desc"), null);
    }

    /**
     * Create an aliased <code>redstonegang_common.permissions_desc</code> table reference
     */
    public PermissionsDesc(String alias) {
        this(DSL.name(alias), PERMISSIONS_DESC);
    }

    /**
     * Create an aliased <code>redstonegang_common.permissions_desc</code> table reference
     */
    public PermissionsDesc(Name alias) {
        this(alias, PERMISSIONS_DESC);
    }

    private PermissionsDesc(Name alias, Table<PermissionsDescRecord> aliased) {
        this(alias, aliased, null);
    }

    private PermissionsDesc(Name alias, Table<PermissionsDescRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> PermissionsDesc(Table<O> child, ForeignKey<O, PermissionsDescRecord> key) {
        super(child, key, PERMISSIONS_DESC);
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
        return Arrays.<Index>asList(Indexes.PERMISSIONS_DESC_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<PermissionsDescRecord> getPrimaryKey() {
        return Keys.KEY_PERMISSIONS_DESC_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<PermissionsDescRecord>> getKeys() {
        return Arrays.<UniqueKey<PermissionsDescRecord>>asList(Keys.KEY_PERMISSIONS_DESC_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionsDesc as(String alias) {
        return new PermissionsDesc(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PermissionsDesc as(Name alias) {
        return new PermissionsDesc(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public PermissionsDesc rename(String name) {
        return new PermissionsDesc(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public PermissionsDesc rename(Name name) {
        return new PermissionsDesc(name, null);
    }
}
