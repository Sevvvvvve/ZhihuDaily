package com.vaynefond.zhihudaily.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date getBeforeDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, -1);
        date = c.getTime();

        return date;
    }

    public static String getFormatDate(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static String getBeforeDay(String date) {
        return getFormatDate(getBeforeDay(getDate(date)));
    }

    public static String formatDate(String dateStr, String format) {
        Date date = getDate(dateStr);
        if (date == null) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }
}
