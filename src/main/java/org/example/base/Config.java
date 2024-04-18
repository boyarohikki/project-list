package org.example.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Config {
    private static final String dateFormatPattern  = "yyyy-MM-dd";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatPattern);

    public static Date parseDate(String date) throws ParseException {
        return simpleDateFormat.parse(date);
    }

    public static String formatDate(Calendar date) {
        return simpleDateFormat.format(date.getTime());
    }
}
