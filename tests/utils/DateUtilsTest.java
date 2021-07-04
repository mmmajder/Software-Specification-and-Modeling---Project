package utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {
    @Test
    void isDateWithinEqualFrom() {
        LocalDate date = LocalDate.of(2021, 6, 20);
        List<LocalDate> dates = getTestDates();

        assertTrue(DateUtils.isDateWithin(date, dates.get(0), dates.get(1)));
    }

    @Test
    void isDateWithinTrue() {
        LocalDate date = LocalDate.of(2021, 6, 25);
        List<LocalDate> dates = getTestDates();

        assertTrue(DateUtils.isDateWithin(date, dates.get(0), dates.get(1)));
    }

    @Test
    void isDateWithinEqualTo() {
        LocalDate date = LocalDate.of(2021, 6, 30);
        List<LocalDate> dates = getTestDates();

        assertTrue(DateUtils.isDateWithin(date, dates.get(0), dates.get(1)));
    }

    @Test
    void isDateWithinFalse() {
        LocalDate date = LocalDate.of(2021, 6, 15);
        List<LocalDate> dates = getTestDates();

        assertFalse(DateUtils.isDateWithin(date, dates.get(0), dates.get(1)));
    }

    private List<LocalDate> getTestDates(){
        List<LocalDate> dates = new ArrayList<>();
        LocalDate from = LocalDate.of(2021, 6, 20);
        LocalDate to = LocalDate.of(2021, 6, 30);
        dates.add(from);
        dates.add(to);

        return dates;
    }
}