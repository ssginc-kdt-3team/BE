package ssginc_kdt_team3.BE.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    public static LocalDateTime findNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);
        return parsedDateTime;
    }

    public static LocalDateTime stringParseLocalDataTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(time, formatter);
    }

    public static LocalDateTime stringParseLocalDataTimeT(String time) {
        String time2 = time.replace("T", " ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(time2, formatter);
    }

    public static LocalTime stringParseLocalTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalTime.parse(time);
    }

    public static LocalDate stringParseLocalDate(String time) {
        return LocalDate.parse(time);
    }

    public static String localDataTimeParseString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = time.format(formatter);
        return format;
    }

}
