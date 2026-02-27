package com.liupeixin.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConverter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String getGMTTime(Date externalDate) {
        return convertGmt(externalDate).format(FORMATTER);
    }

    public static LocalDateTime convertGmt(Date externalDate) {
        Instant instant = externalDate.toInstant();
        ZonedDateTime gmtTime = instant.atZone(ZoneId.of("GMT"));
        return gmtTime.toLocalDateTime();
    }

    public static String getNewYorkTime(Date externalDate) {
        return convertGmtToNewYork(externalDate).format(FORMATTER);
    }

    public static LocalDateTime convertGmtToNewYork(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime gmtTime = instant.atZone(ZoneId.of("GMT"));
        return gmtTime.withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }
    
}
