/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables;


import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Indexes;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.Keys;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.RedstonegangCommon;
import dev.feldmann.redstonegang.common.db.jooq.redstonegang_common.tables.records.GroupOptionsRecord;

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
public class GroupOptions extends TableImpl<GroupOptionsRecord> {

    private static final long serialVersionUID = 772823601;

    /**
     * The reference instance of <code>redstonegang_common.group_options</code>
     */
    public static final GroupOptions GROUP_OPTIONS = new GroupOptions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GroupOptionsRecord> getRecordType() {
        return GroupOptionsRecord.class;
    }

    /**
     * The column <code>redstonegang_common.group_options.group</code>.
     */
    public final TableField<GroupOptionsRecord, Integer> GROUP = createField("group", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.group_options.key</code>.
     */
    public final TableField<GroupOptionsRecord, String> KEY = createField("key", org.jooq.impl.SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.group_options.value</code>.
     */
    public final TableField<GroupOptionsRecord, Double> VALUE = createField("value", org.jooq.impl.SQLDataType.DOUBLE.defaultValue(org.jooq.impl.DSL.inline("NULL", org.jooq.impl.SQLDataType.DOUBLE)), this, "");

    /**
     * Create a <code>redstonegang_common.group_options</code> table reference
     */
    public GroupOptions() {
        this(DSL.name("group_options"), null);
    }

    /**
     * Create an aliased <code>redstonegang_common.group_options</code> table reference
     */
    public GroupOptions(String alias) {
        this(DSL.name(alias), GROUP_OPTIONS);
    }

    /**
     * Create an aliased <code>redstonegang_common.group_options</code> table reference
     */
    public GroupOptions(Name alias) {
        this(alias, GROUP_OPTIONS);
    }

    private GroupOptions(Name alias, Table<GroupOptionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private GroupOptions(Name alias, Table<GroupOptionsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> GroupOptions(Table<O> child, ForeignKey<O, GroupOptionsRecord> key) {
        super(child, key, GROUP_OPTIONS);
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
        return Arrays.<Index>asList(Indexes.GROUP_OPTIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<GroupOptionsRecord> getPrimaryKey() {
        return Keys.KEY_GROUP_OPTIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<GroupOptionsRecord>> getKeys() {
        return Arrays.<UniqueKey<GroupOptionsRecord>>asList(Keys.KEY_GROUP_OPTIONS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ForeignKey<GroupOptionsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<GroupOptionsRecord, ?>>asList(Keys.FK_GROUP);
    }

    public Groups groups() {
        return new Groups(this, Keys.FK_GROUP);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupOptions as(String alias) {
        return new GroupOptions(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupOptions as(Name alias) {
        return new GroupOptions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public GroupOptions rename(String name) {
        return new GroupOptions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public GroupOptions rename(Name name) {
        return new GroupOptions(name, null);
    }
}
