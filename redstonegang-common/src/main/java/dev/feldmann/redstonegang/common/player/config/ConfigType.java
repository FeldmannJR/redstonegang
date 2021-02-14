package dev.feldmann.redstonegang.common.player.config;

import dev.feldmann.redstonegang.common.db.Database;
import org.jooq.*;
import org.jooq.impl.DSL;

public abstract class ConfigType<T, D> {

    private String name;
    private T defaultValue;
    private Field<D> field;
    boolean loaded = false;
    Class<? extends T> valorType;

    public ConfigType(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.valorType = (Class<? extends T>) defaultValue.getClass();
    }

    public String getName() {
        return name;
    }

    public Class<? extends T> getValorType() {
        return valorType;
    }

    public T getDefault() {
        return defaultValue;
    }

    public abstract T convert(D v);

    public abstract D convertToDb(T v);

    public T next(T t) {
        return null;
    }

    public Field<D> getField() {
        if (this.field == null) {
            field = DSL.field(getColumnName(), getDataType().defaultValue(convertToDb(defaultValue)).nullable(getDefault() == null));
        }
        return field;
    }

    public T getValue(Record r) {
        return convert(r.get(getField()));
    }

    public void alterTable(Database database, Table t) {
        database.createColumnIfNotExists(t, getField());
    }

    private String getColumnName() {
        return "cfg_" + name;
    }

    protected abstract DataType<D> getDataType();


}
