package controller;

import model.Library;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportsTest {

    @Test
    void generateFile() throws IOException {
        Library library = new Library();
        List<String> lines = new ArrayList<>();
        String line1 = "asdadad";
        String line2 = "asdadad";
        String line3 = "saccdscdcds";

        lines.add(line1);
        lines.add(line2);
        lines.add(line3);

        String filename = "file1";
        Reports reports = new Reports(library);

        reports.generateFile(lines, filename);

        File tempFile = new File("reports/file1.txt");
        assertTrue(tempFile.exists());
    }
}