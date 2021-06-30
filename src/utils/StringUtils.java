package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    static public boolean isNullOrEmpty(String value) {
        return value == null || value.equals("");
    }

    static public String dateToString(LocalDate date, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }
}
