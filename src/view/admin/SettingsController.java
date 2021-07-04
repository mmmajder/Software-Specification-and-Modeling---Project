package view.admin;

import controller.AdminSettingsController;
import controller.Reports;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Account;
import model.Library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsController {
    public TextField regularNumberOfDays;
    public TextField studentNumberOfDays;
    public TextField retiredNumberOfDays;
    public TextField regularBooks;
    public TextField studentBooks;
    public TextField retiredBooks;
    public TextField regular6price;
    public TextField student6price;
    public TextField retired6price;
    public TextField regular12price;
    public TextField student12price;
    public TextField retired12price;
    public TextField preschoolerNumberOfDays;
    public TextField pupilNumberOfDays;
    public TextField preschoolerBooks;
    public TextField pupilBooks;
    public TextField preschooler6price;
    public TextField pupil6price;
    public TextField preschooler12price;
    public TextField pupil12price;
    public TextField privilegedNumberOfDays;
    public TextField privilegedBooks;
    public TextField privileged6price;
    public TextField privileged12price;

    public Account account;
    public Library library;
    public AdminSettingsController adminSettingsController;
    public DatePicker startDate;
    public DatePicker endDate;
    public Reports reports;
    public Button btnEditionsIsuues;
    public Button btnTop10;
    public Button btnDailyReport;

    public void initData(Account account) {
        this.account = account;
        this.library = new Library();
        this.adminSettingsController = new AdminSettingsController(library);
        this.reports = new Reports(library);
    }

    @FXML
    public void saveChanges() {
        adminSettingsController.updateMaxIssueDays(getMaxIssueDaysData());
        adminSettingsController.updateMaxIssuedBooks(getMaxBooksData());
        adminSettingsController.updatePriceCatalog(get6monthsPrices(), get12monthsPrices());
    }

    private List<Integer> getMaxIssueDaysData() {
        List<Integer> maxIssueDays = new ArrayList<>();
        maxIssueDays.add(Integer.valueOf(regularNumberOfDays.getText()));
        maxIssueDays.add(Integer.valueOf(studentNumberOfDays.getText()));
        maxIssueDays.add(Integer.valueOf(preschoolerNumberOfDays.getText()));
        maxIssueDays.add(Integer.valueOf(pupilNumberOfDays.getText()));
        maxIssueDays.add(Integer.valueOf(retiredNumberOfDays.getText()));
        maxIssueDays.add(Integer.valueOf(privilegedNumberOfDays.getText()));
        return maxIssueDays;
    }

    private List<Integer> getMaxBooksData() {
        List<Integer> maxBooks = new ArrayList<>();
        maxBooks.add(Integer.valueOf(regularBooks.getText()));
        maxBooks.add(Integer.valueOf(studentBooks.getText()));
        maxBooks.add(Integer.valueOf(preschoolerBooks.getText()));
        maxBooks.add(Integer.valueOf(pupilBooks.getText()));
        maxBooks.add(Integer.valueOf(retiredBooks.getText()));
        maxBooks.add(Integer.valueOf(privilegedBooks.getText()));
        return maxBooks;
    }

    private List<Double> get6monthsPrices() {
        List<Double> prices6months = new ArrayList<>();
        prices6months.add(Double.valueOf(regular6price.getText()));
        prices6months.add(Double.valueOf(student6price.getText()));
        prices6months.add(Double.valueOf(preschooler6price.getText()));
        prices6months.add(Double.valueOf(pupil6price.getText()));
        prices6months.add(Double.valueOf(regular6price.getText()));
        prices6months.add(Double.valueOf(privileged6price.getText()));
        return prices6months;
    }

    private List<Double> get12monthsPrices() {
        List<Double> prices12months = new ArrayList<>();
        prices12months.add(Double.valueOf(regular12price.getText()));
        prices12months.add(Double.valueOf(student12price.getText()));
        prices12months.add(Double.valueOf(preschooler12price.getText()));
        prices12months.add(Double.valueOf(pupil12price.getText()));
        prices12months.add(Double.valueOf(retired12price.getText()));
        prices12months.add(Double.valueOf(privileged12price.getText()));
        return prices12months;
    }

    @FXML
    public void generateDailyReports() throws IOException {
        this.reports.generateDailyReport();
    }

    @FXML
    public void generateTop10Reports() throws IOException {
        this.reports.generateTopTenEditionsReport(startDate.getValue(), endDate.getValue());
    }

    @FXML
    public void generateEditionsIssuesReports() throws IOException {
        this.reports.generateEditionIssuesReport(startDate.getValue(), endDate.getValue(), null);
    }
}
