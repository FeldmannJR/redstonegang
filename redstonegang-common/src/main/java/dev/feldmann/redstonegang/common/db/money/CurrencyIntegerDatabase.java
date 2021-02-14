package dev.feldmann.redstonegang.common.db.money;

import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public interface CurrencyIntegerDatabase extends CurrencyDatabase<Integer> {
    @Override
    default Field<Integer> getValueField() {
        return DSL.field("value", SQLDataType.INTEGER.defaultValue(getDefaultValue()).nullable(false));
    }

    @Override
    default Integer getDefaultValue() {
        return 0;
    }
}
