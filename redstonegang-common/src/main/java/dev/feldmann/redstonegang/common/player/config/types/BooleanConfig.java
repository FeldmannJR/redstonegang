package dev.feldmann.redstonegang.common.player.config.types;

import dev.feldmann.redstonegang.common.player.config.ConfigType;
import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

public class BooleanConfig extends ConfigType<Boolean, Byte> {

    public BooleanConfig(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public Boolean convert(Byte v) {
        return v == 1 ? true : false;
    }

    @Override
    public Byte convertToDb(Boolean v) {
        return v ? (byte) 1 : (byte) 0;
    }

    @Override
    protected DataType<Byte> getDataType() {
        return SQLDataType.TINYINT;
    }

    @Override
    public Boolean next(Boolean aBoolean) {
        return !aBoolean;
    }
}
