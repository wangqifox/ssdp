package io.github.wangqifox.ssdp.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author wangqi
 * @date 2021/1/11
 */
public class TimeUtils {
    public static final ZoneId GMT = ZoneId.of("GMT");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US).withZone(GMT);

    public static String format(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return DATE_FORMATTER.format(ZonedDateTime.ofInstant(localDateTime.toInstant(ZoneOffset.UTC), GMT));
    }

    public static LocalDateTime parse(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_FORMATTER);
    }
}

