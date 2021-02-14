package dev.feldmann.redstonegang.common.player.config.types;

import dev.feldmann.redstonegang.common.player.config.SimpleConfigType;
import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

public class StringConfig extends SimpleConfigType<String> {

    public StringConfig(String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    protected DataType<String> getDataType() {
        return SQLDataType.VARCHAR(255);
    }
}
