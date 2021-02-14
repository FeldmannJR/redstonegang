package dev.feldmann.redstonegang.common.utils;

public class EnumUtils {

    public static <C extends Enum<C>> C next(C t) {
        C[] values = values(t);
        int slot = t.ordinal() + 1;
        if (slot >= values.length) {
            return values[0];
        }
        return values[slot];
    }

    public static <C extends Enum<C>> C prev(C t) {
        C[] values = values(t);
        int slot = t.ordinal() - 1;
        if (slot < 0) {
            return values[values.length - 1];
        }
        return values[slot];
    }


    private static <C extends Enum<C>> C[] values(C example) {
        Class<? extends Enum> valorType = example.getClass();
        Enum[] en = valorType.getEnumConstants();

        return (C[]) en;
    }
}
