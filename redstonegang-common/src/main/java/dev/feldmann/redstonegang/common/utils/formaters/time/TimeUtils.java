package dev.feldmann.redstonegang.common.utils.formaters.time;

import dev.feldmann.redstonegang.common.utils.formaters.numbers.NumberUtils;

public class TimeUtils {

    private final static long ONE_SECOND = 1000;
    private final static long SECONDS = 60;
    private final static long ONE_MINUTE = ONE_SECOND * 60;
    private final static long MINUTES = 60;
    private final static long ONE_HOUR = ONE_MINUTE * 60;
    private final static long HOURS = 24;
    private final static long ONE_DAY = ONE_HOUR * 24;

    public static String millisToString(long duration) {
        StringBuffer res = new StringBuffer();
        long temp = 0;
        if (duration >= ONE_SECOND) {
            temp = duration / ONE_DAY;
            if (temp > 0) {
                duration -= temp * ONE_DAY;
                res.append(temp).append(" dia").append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_HOUR;
            if (temp > 0) {
                duration -= temp * ONE_HOUR;
                res.append(temp).append(" hora").append(temp > 1 ? "s" : "")
                        .append(duration >= ONE_MINUTE ? ", " : "");
            }

            temp = duration / ONE_MINUTE;
            if (temp > 0) {
                duration -= temp * ONE_MINUTE;
                res.append(temp).append(" minuto").append(temp > 1 ? "s" : "");
            }

            if (!res.toString().equals("") && duration >= ONE_SECOND) {
                res.append(" e ");
            }
            temp = duration / ONE_SECOND;
            if (temp > 0) {
                res.append(temp).append(" segundo").append(temp > 1 ? "s" : "");
            }
            return res.toString();
        } else {
            return "0 segundos";
        }
    }

    public static String millisToHoursAndMinutes(long millis) {
        long horas = millis / ONE_HOUR;
        long minutos = (millis % ONE_HOUR) / ONE_MINUTE;
        if (horas >0) {
            return horas + "h e " + minutos + "m";
        } else {
            return minutos + "m";
        }
    }


    public static boolean elapsed(long from, long required) {
        return System.currentTimeMillis() - from > required;
    }

    /**
     * @param base   the time base unit
     * @param string the string with or without time suffix
     * @return Long The time in @param base
     */
    public static Long convertFromString(String string, TimeUnit base) {
        if (string.isEmpty()) return null;
        char end = string.charAt(string.length() - 1);
        long mp = 0;
        String val;
        if (end >= '0' && end <= '9') {
            mp = 1;
            val = string;
        } else {
            if (string.length() == 1) {
                return null;
            }
            for (TimeUnit t : TimeUnit.values()) {
                if (t.suffix != null && t.suffix == end) {
                    mp = t.mp;
                    break;
                }
            }
            if (mp == 0) {
                return null;
            }
            if (base.mp > mp) {
                return null;
            }
            mp = mp / base.mp;
            val = string.substring(0, string.length() - 1);
        }
        try {
            Long value = Long.valueOf(val);
            return value * mp;
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    /**
     * @return String represeting the time in unity @param type, if type is null return the larger
     */
    public static String convertString(long time, int trim, TimeUnit type) {
        if (time <= 0) {
            return "Permanente";
        }

        if (type == null) {
            for (TimeUnit t : TimeUnit.values()) {
                if (time >= t.mp) {
                    type = t;
                }
            }
        }
        if (type == null) {
            return "";
        }

        double value = (double) time / type.mp;
        return NumberUtils.roundMax(value, trim) + " " + type.getNome(value);
    }


}
