package dev.feldmann.redstonegang.common.utils.formaters.numbers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        if (places == 0) return (long) value;
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double roundFloor(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        if (places == 0) return (long) value;
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.FLOOR);
        return bd.doubleValue();
    }

    public static String roundMax(double value, int maxPlaces) {
        if (maxPlaces < 0) throw new IllegalArgumentException();
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return (long) value + "";
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(maxPlaces, RoundingMode.HALF_UP);
        return "" + bd.doubleValue();
    }

    public static Integer convertFromString(String string) {
        if (string.isEmpty()) return null;
        char end = string.charAt(string.length() - 1);
        int mp = 0;
        if (end >= '0' && end <= '9') {
            return integerFromString(string);
        } else {
            if (string.length() == 1) {
                return null;
            }
            for (NumberUnit unit : NumberUnit.values()) {
                if (unit.suffix != null && unit.suffix == end) {
                    mp = unit.mp;
                    break;
                }
            }
            if (mp == 0) {
                return null;
            }
            string = string.substring(0, string.length() - 1);
        }
        try {
            Integer value = Integer.valueOf(string);
            return value * mp;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static String convertToString(long value) {
        NumberUnit type = null;
        for (NumberUnit unit : NumberUnit.values()) {
            if (unit.suffix != null && value >= unit.mp) {
                type = unit;
            }
        }
        if (type == null) {
            return value + "";
        }
        double v = (double) value / type.mp;
        if (v == (long) v) {
            return (long) v + "" + type.suffix;
        }

        return roundFloor(v, 1) + "" + type.suffix;
    }

    public static String convertToString(double d) {
        int i = (int) d;
        // é numero inteiro
        if (d % i == 0) {
            return convertToString((long) d);
        }
        return d + "";
    }


    public static Integer integerFromString(String s) {
        if (s != null) {
            s = s.trim();
            try {
                return Integer.valueOf(s);
            } catch (NumberFormatException ex) {

            }
        }
        return null;
    }

    public static Double doubleFromString(String s) {
        if (s != null) {
            try {
                s = s.trim();
                return Double.valueOf(s);
            } catch (NumberFormatException ex) {

            }
        }
        return null;
    }

    public static String formatWithK(int t) {
        if (t >= 1000) {
            if (t % 1000 == 0) {
                return (t / 1000) + "k";
            }
            if (t % 100 == 0) {
                int k = (t / 1000);
                int resto = t - k;
                return k + "." + (resto / 100) + "k";
            }
        }
        return "" + t;
    }


}
