/*
 * This file is generated by jOOQ.
 */
package dev.feldmann.redstonegang.wire.game.base.addonconfigs.db.tables;


import dev.feldmann.redstonegang.wire.game.base.addonconfigs.db.Indexes;
import dev.feldmann.redstonegang.wire.game.base.addonconfigs.db.Keys;
import dev.feldmann.redstonegang.wire.game.base.addonconfigs.db.RedstonegangCommon;
import dev.feldmann.redstonegang.wire.game.base.addonconfigs.db.tables.records.AddonsConfigsRecord;

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
public class AddonsConfigs extends TableImpl<AddonsConfigsRecord> {

    private static final long serialVersionUID = -1975149970;

    /**
     * The reference redstonegang of <code>redstonegang_common.addons_configs</code>
     */
    public static final AddonsConfigs ADDONS_CONFIGS = new AddonsConfigs();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AddonsConfigsRecord> getRecordType() {
        return AddonsConfigsRecord.class;
    }

    /**
     * The column <code>redstonegang_common.addons_configs.key</code>.
     */
    public final TableField<AddonsConfigsRecord, String> KEY = createField("key", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.addons_configs.addon</code>.
     */
    public final TableField<AddonsConfigsRecord, String> ADDON = createField("addon", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.addons_configs.server</code>.
     */
    public final TableField<AddonsConfigsRecord, String> SERVER = createField("server", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>redstonegang_common.addons_configs.value</code>.
     */
    public final TableField<AddonsConfigsRecord, String> VALUE = createField("value", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>redstonegang_common.addons_configs</code> table reference
     */
    public AddonsConfigs() {
        this(DSL.name("addons_configs"), null);
    }

    /**
     * Create an aliased <code>redstonegang_common.addons_configs</code> table reference
     */
    public AddonsConfigs(String alias) {
        this(DSL.name(alias), ADDONS_CONFIGS);
    }

    /**
     * Create an aliased <code>redstonegang_common.addons_configs</code> table reference
     */
    public AddonsConfigs(Name alias) {
        this(alias, ADDONS_CONFIGS);
    }

    private AddonsConfigs(Name alias, Table<AddonsConfigsRecord> aliased) {
        this(alias, aliased, null);
    }

    private AddonsConfigs(Name alias, Table<AddonsConfigsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> AddonsConfigs(Table<O> child, ForeignKey<O, AddonsConfigsRecord> key) {
        super(child, key, ADDONS_CONFIGS);
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
        return Arrays.<Index>asList(Indexes.ADDONS_CONFIGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<AddonsConfigsRecord> getPrimaryKey() {
        return Keys.KEY_ADDONS_CONFIGS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<AddonsConfigsRecord>> getKeys() {
        return Arrays.<UniqueKey<AddonsConfigsRecord>>asList(Keys.KEY_ADDONS_CONFIGS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddonsConfigs as(String alias) {
        return new AddonsConfigs(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddonsConfigs as(Name alias) {
        return new AddonsConfigs(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AddonsConfigs rename(String name) {
        return new AddonsConfigs(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AddonsConfigs rename(Name name) {
        return new AddonsConfigs(name, null);
    }
}
