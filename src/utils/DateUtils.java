package utils;

import java.time.LocalDate;

public class DateUtils {

    static public boolean isDateWithin(LocalDate date, LocalDate from, LocalDate to){
        return (date.isEqual(from) || date.isAfter(from)) && (date.isBefore(to) || date.isEqual(to));
    }
}
