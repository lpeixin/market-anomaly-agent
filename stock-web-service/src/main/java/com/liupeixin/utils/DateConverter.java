package com.liupeixin.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateConverter {
    public static LocalDateTime convertGmtToNewYork(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime gmtTime = instant.atZone(ZoneId.of("GMT"));
        return gmtTime.withZoneSameInstant(ZoneId.of("America/New_York")).toLocalDateTime();
    }
}
