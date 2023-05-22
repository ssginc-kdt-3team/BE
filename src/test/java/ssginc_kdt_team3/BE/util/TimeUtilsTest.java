package ssginc_kdt_team3.BE.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
}