package ssginc_kdt_team3.BE.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @Test
    void stringParseLocalTime() {
        String time = "15:00";
        LocalTime localTime = TimeUtils.stringParseLocalTime(time);
        System.out.println(localTime);
    }

    @Test
    void stringParseLocalDate() {
        String time = "2022-11-11";
        LocalDate localTime = TimeUtils.stringParseLocalDate(time);
        System.out.println(localTime);
    }

    @Test
    void StringParseLocalDateTime() {
        String time = "2023-05-30T08:55:17";
        LocalDateTime localDateTime = TimeUtils.stringParseLocalDataTimeT(time);
        System.out.println(localDateTime);

        LocalDateTime now = TimeUtils.findNow();
        System.out.println("now = " + now);
    }
}