package dev.feldmann.redstonegang.common.db.money;

import org.jooq.Field;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public interface CurrencyDoubleDatabase extends CurrencyDatabase<Double> {
    @Override
    default Field<Double> getValueField() {
        return DSL.field("value", SQLDataType.DOUBLE.defaultValue(getDefaultValue()).nullable(false));
    }

    @Override
    default Double getDefaultValue() {
        return 0D;
    }
}
