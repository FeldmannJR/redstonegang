package dev.feldmann.redstonegang.common.utils;

import java.lang.reflect.Array;

public class ArrayUtils {

    public static <T> T[] insertFirst(T[] array, T... el) {
        if (el == null) return null;
        T[] newarray = (T[]) new Object[array.length + el.length];
        System.arraycopy(array, 0, newarray, el.length, array.length);
        for (int x = 0; x < el.length; x++) {
            newarray[x] = el[x];
        }
        return newarray;
    }
}
