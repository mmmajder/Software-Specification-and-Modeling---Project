package view.admin;

import controller.AdminSettingsController;
import controller.Reports;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Account;
import model.Library;
import model.enums.MemberType;
import repository.ILibraryRepo;
import repository.LibraryRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    public ILibraryRepo libraryRepo;

    public void initData(Account account) {
        this.account = account;
        this.library = new Library();
        this.adminSettingsController = new AdminSettingsController(library);
        this.reports = new Reports(library);
        this.libraryRepo = new LibraryRepo();
        this.libraryRepo.loadAccounts(library);
        this.libraryRepo.loadPersons(library);
        this.libraryRepo.loadEditions(library);
        this.libraryRepo.loadBooks(library);
        this.libraryRepo.loadPriceCatalogs(library);
        this.libraryRepo.loadFullYearPrices(library);
        this.libraryRepo.loadHalfAYearPrices(library);
        this.libraryRepo.loadMaxIssueDays(library);
        this.libraryRepo.loadMaxIssuedBooks(library);
        this.libraryRepo.loadIssuedBooks(library);
        this.libraryRepo.loadPayments(library);

        this.setNumberOfDays();
        this.setMaxNumberOfBooks();
        this.set6monthsPrices();
        this.set12monthsPrices();
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

    private void setNumberOfDays() {
        HashMap<MemberType, Integer> maxIssueDays = library.getMaxIssueDays();
        regularNumberOfDays.setText(String.valueOf(maxIssueDays.get(MemberType.REGULAR)));
        studentNumberOfDays.setText(String.valueOf(maxIssueDays.get(MemberType.STUDENT)));
        preschoolerNumberOfDays.setText(String.valueOf(maxIssueDays.get(MemberType.PRESCHOOLER)));
        pupilNumberOfDays.setText(String.valueOf(maxIssueDays.get(MemberType.PUPIL)));
        retiredNumberOfDays.setText(String.valueOf(maxIssueDays.get(MemberType.RETIRED)));
        privilegedNumberOfDays.setText(String.valueOf(maxIssueDays.get(MemberType.PRIVILEGED)));
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

    private void setMaxNumberOfBooks() {
        HashMap<MemberType, Integer> maxNumberOfBooks = library.getMaxIssueDays();
        regularBooks.setText(String.valueOf(maxNumberOfBooks.get(MemberType.REGULAR)));
        studentBooks.setText(String.valueOf(maxNumberOfBooks.get(MemberType.STUDENT)));
        preschoolerBooks.setText(String.valueOf(maxNumberOfBooks.get(MemberType.PRESCHOOLER)));
        pupilBooks.setText(String.valueOf(maxNumberOfBooks.get(MemberType.PUPIL)));
        retiredBooks.setText(String.valueOf(maxNumberOfBooks.get(MemberType.RETIRED)));
        privilegedBooks.setText(String.valueOf(maxNumberOfBooks.get(MemberType.PRIVILEGED)));
    }

    private List<Double> get6monthsPrices() {
        List<Double> prices6months = new ArrayList<>();
        prices6months.add(Double.valueOf(regular6price.getText()));
        prices6months.add(Double.valueOf(student6price.getText()));
        prices6months.add(Double.valueOf(preschooler6price.getText()));
        prices6months.add(Double.valueOf(pupil6price.getText()));
        prices6months.add(Double.valueOf(retired6price.getText()));
        prices6months.add(Double.valueOf(privileged6price.getText()));
        return prices6months;
    }

    private void set6monthsPrices() {
        regular6price.setText(String.valueOf(library.getHalfAYearPrice(MemberType.REGULAR)));
        student6price.setText(String.valueOf(library.getHalfAYearPrice(MemberType.STUDENT)));
        preschooler6price.setText(String.valueOf(library.getHalfAYearPrice(MemberType.PRESCHOOLER)));
        pupil6price.setText(String.valueOf(library.getHalfAYearPrice(MemberType.PUPIL)));
        retired6price.setText(String.valueOf(library.getHalfAYearPrice(MemberType.RETIRED)));
        privileged6price.setText(String.valueOf(library.getHalfAYearPrice(MemberType.PRIVILEGED)));
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

    private void set12monthsPrices() {
        regular12price.setText(String.valueOf(library.getFullYearPrice(MemberType.REGULAR)));
        student12price.setText(String.valueOf(library.getFullYearPrice(MemberType.STUDENT)));
        preschooler12price.setText(String.valueOf(library.getFullYearPrice(MemberType.PRESCHOOLER)));
        pupil12price.setText(String.valueOf(library.getFullYearPrice(MemberType.PUPIL)));
        retired12price.setText(String.valueOf(library.getFullYearPrice(MemberType.RETIRED)));
        privileged12price.setText(String.valueOf(library.getFullYearPrice(MemberType.PRIVILEGED)));
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
