package com.esgi.astrologia.Services;

import java.util.Calendar;
import java.util.Locale;

public final class CalendarServices {
    public static String calendarToString(Calendar calendar) {
        String result = null;

        if(calendar != null) {
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.format(Locale.FRANCE, "%02d", (calendar.get(Calendar.MONTH) + 1));
            String day = String.format(Locale.FRANCE, "%02d", calendar.get(Calendar.DAY_OF_MONTH));

            result = day + "-" + month + "-" + year;
        }

        return result;
    }

    public static Calendar stringToCalendar_en(String string) {
        Calendar result = null;

        if(string.length() == 10) {
            String[] array = string.split("-");

            int year = Integer.parseInt(array[0]);
            int month = Integer.parseInt(array[1]);
            int day = Integer.parseInt(array[2]);

            result = Calendar.getInstance();
            result.set(Calendar.YEAR, year);
            result.set(Calendar.MONTH, month - 1);
            result.set(Calendar.DAY_OF_MONTH, day);
        }

        return result;
    }
}
