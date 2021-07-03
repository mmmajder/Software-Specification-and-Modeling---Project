package controller;

import model.Library;
import utils.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class Reports {

    private final String path = "../../reports/";
    private final String extension = ".txt";
    private final String datePattern = "dd_mm_yyyy";
    private Library library;

    public Reports(Library library) {
        this.library = library;
    }


    public void generateTodaysReport(){
        generateDailyReport(LocalDate.now());
    }
    private void generateDailyReport(LocalDate date){
        Issu
    }

    public void generateTopTenBooks(){

    }

    private String generateName(){
        return "Daily_" + getTodaysDateStr();
    }

    private String generateName(List<LocalDate> dates){
        String name = "Top10_" + getTodaysDateStr();
        name += "_from_" + getDateStr(dates.get(0));
        name += "_to_" + getDateStr(dates.get(1));

        return name;
    }

    private String getTodaysDateStr(){
        return getDateStr(LocalDate.now());
    }

    private String getDateStr(LocalDate date){
        return StringUtils.dateToString(date, datePattern);
    }

    private void generateFile(List<String> lines, String filename) throws IOException {
        Files.write(Paths.get(path + filename + extension), lines, StandardCharsets.UTF_8);
    }
}
