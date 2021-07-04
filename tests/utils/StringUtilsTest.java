package utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void isNullOrEmptyNull() {
        String string = null;

        assertTrue(StringUtils.isNullOrEmpty(string));
    }

    @Test
    void isNullOrEmptyEmpty() {
        String string = "";

        assertTrue(StringUtils.isNullOrEmpty(string));
    }

    @Test
    void dateToString() {
        LocalDate date = LocalDate.of(2021, 6, 25);
        String patter = "dd.MM.yyyy.";

        assertEquals(StringUtils.dateToString(date, patter), "25.06.2021.");
    }
}