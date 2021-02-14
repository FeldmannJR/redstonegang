package dev.feldmann.redstonegang.common.utils.formaters;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static DateFormat dayFormater = null;
    private static DateFormat timeFormater = null;

    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String formatDate(Timestamp tmp) {
        if (dayFormater == null) {
            dayFormater = new SimpleDateFormat("dd/MM/yyyy");
        }
        return dayFormater.format(tmp);
    }

    public static Calendar getCalendarWithoutTime() {
        Calendar c = Calendar.getInstance();
        for (int field : new int[]{Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND}) {
            c.set(field, c.getActualMinimum(field));
        }
        return c;

    }

    public static String formatTime(Timestamp tmp) {
        if (timeFormater == null) {
            timeFormater = new SimpleDateFormat("dd/MM/yyyy H:m");
        }
        return timeFormater.format(tmp);
    }

    public static String currentTime() {
        return formatTime(getCurrentTime());
    }

    public static boolean isBefore(Timestamp t, int dia, int mes, int ano) {
        Calendar when = Calendar.getInstance();
        when.set(ano, mes - 1, dia, 0, 0, 0);
        return t.before(when.getTime());

    }

    public static Timestamp addMinutesToDate(Timestamp date, int minutes) {
        final long ONE_MINUTE_IN_MILLIS = 60000; // millisecs
        long curTimeInMs = date.getTime();
        Long sumTimeInMs = Long.valueOf(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return new Timestamp(sumTimeInMs);
    }

}
