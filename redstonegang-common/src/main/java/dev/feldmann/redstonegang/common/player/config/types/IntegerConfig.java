package dev.feldmann.redstonegang.common.player.config.types;

import dev.feldmann.redstonegang.common.player.config.SimpleConfigType;
import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

public class IntegerConfig extends SimpleConfigType<Integer> {

    public IntegerConfig(String name, Integer defaultValue) {
        super(name, defaultValue);
    }

    @Override
    protected DataType<Integer> getDataType() {
        return SQLDataType.INTEGER;
    }
}
