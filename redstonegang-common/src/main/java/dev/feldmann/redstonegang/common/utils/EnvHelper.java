package dev.feldmann.redstonegang.common.utils;

public class EnvHelper {


    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key, "" + defaultValue);
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1")) {
            return true;
        }
        if (value.equalsIgnoreCase("false") || value.equalsIgnoreCase("0")) {
            return false;
        }
        return defaultValue;
    }

    public static String get(String key, String defaultValue) {
        String value = System.getenv(key);
        if (value != null && value.isEmpty()) {
            value = null;
        }
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }
}
