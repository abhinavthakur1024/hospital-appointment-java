package com.csg.hospital.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isValid(String isoMinute) {
        try {
            LocalDateTime.parse(isoMinute, FMT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String now() {
        return LocalDateTime.now().format(FMT);
    }
}