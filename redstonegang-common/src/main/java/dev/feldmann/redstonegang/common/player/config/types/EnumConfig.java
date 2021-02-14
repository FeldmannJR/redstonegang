package dev.feldmann.redstonegang.common.player.config.types;

import dev.feldmann.redstonegang.common.player.config.ConfigType;
import dev.feldmann.redstonegang.common.utils.EnumUtils;
import org.jooq.DataType;
import org.jooq.impl.SQLDataType;

public class EnumConfig<C extends Enum<C>> extends ConfigType<C, Integer> {

    public EnumConfig(String name, C defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public C convert(Integer v) {
        return values()[v];
    }

    public C[] values() {
        Class<? extends C> valorType = getValorType();
        C[] en = valorType.getEnumConstants();
        return en;
    }

    @Override
    public Integer convertToDb(C v) {
        return v.ordinal();
    }

    @Override
    protected DataType getDataType() {
        return SQLDataType.INTEGER;
    }


    public String getName(C c) {
        return c.name();
    }

    @Override
    public C next(C c) {
        return EnumUtils.next(c);
    }
}
