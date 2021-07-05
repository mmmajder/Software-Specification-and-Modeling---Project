package controller;

import model.Library;
import model.PriceCatalog;
import model.enums.MemberType;
import repository.ILibraryRepo;
import repository.LibraryRepo;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminSettingsController {

    private Library library;
    private ILibraryRepo libraryRepo;

    public AdminSettingsController(Library library) {
        this.library = library;
        this.libraryRepo = new LibraryRepo();
    }

    public void updateMaxIssueDays(List<Integer> maxIssueDays) {
        setNewMaxIssueDays(MemberType.REGULAR, maxIssueDays.get(0));
        libraryRepo.updateMaxIssueDay(MemberType.REGULAR, maxIssueDays.get(0));
        setNewMaxIssueDays(MemberType.STUDENT, maxIssueDays.get(1));
        libraryRepo.updateMaxIssueDay(MemberType.STUDENT, maxIssueDays.get(1));
        setNewMaxIssueDays(MemberType.PRESCHOOLER, maxIssueDays.get(2));
        libraryRepo.updateMaxIssueDay(MemberType.PRESCHOOLER, maxIssueDays.get(2));
        setNewMaxIssueDays(MemberType.PUPIL, maxIssueDays.get(3));
        libraryRepo.updateMaxIssueDay(MemberType.PUPIL, maxIssueDays.get(3));
        setNewMaxIssueDays(MemberType.RETIRED, maxIssueDays.get(4));
        libraryRepo.updateMaxIssueDay(MemberType.RETIRED, maxIssueDays.get(4));
        setNewMaxIssueDays(MemberType.PRIVILEGED, maxIssueDays.get(5));
        libraryRepo.updateMaxIssueDay(MemberType.PRIVILEGED, maxIssueDays.get(5));
    }

    private void setNewMaxIssueDays(MemberType type, Integer newMaxIssueDays) {
        if (newMaxIssueDays != null) {
            library.setMaxIssueDays(type, newMaxIssueDays);
        }
    }

    public void updateMaxIssuedBooks(List<Integer> maxIssuedBooks) {
        setNewMaxIssuedBooks(MemberType.REGULAR, maxIssuedBooks.get(0));
        libraryRepo.updateMaxIssuedBooks(MemberType.REGULAR, maxIssuedBooks.get(0));
        setNewMaxIssuedBooks(MemberType.STUDENT, maxIssuedBooks.get(1));
        libraryRepo.updateMaxIssuedBooks(MemberType.STUDENT, maxIssuedBooks.get(1));
        setNewMaxIssuedBooks(MemberType.PRESCHOOLER, maxIssuedBooks.get(2));
        libraryRepo.updateMaxIssuedBooks(MemberType.PRESCHOOLER, maxIssuedBooks.get(2));
        setNewMaxIssuedBooks(MemberType.PUPIL, maxIssuedBooks.get(3));
        libraryRepo.updateMaxIssuedBooks(MemberType.PUPIL, maxIssuedBooks.get(3));
        setNewMaxIssuedBooks(MemberType.RETIRED, maxIssuedBooks.get(4));
        libraryRepo.updateMaxIssuedBooks(MemberType.RETIRED, maxIssuedBooks.get(4));
        setNewMaxIssuedBooks(MemberType.PRIVILEGED, maxIssuedBooks.get(5));
        libraryRepo.updateMaxIssuedBooks(MemberType.PRIVILEGED, maxIssuedBooks.get(5));
    }

    private void setNewMaxIssuedBooks(MemberType type, Integer newMaxIssuedBooks) {
        if (newMaxIssuedBooks != null) {
            library.setMaxIssuedBooks(type, newMaxIssuedBooks);
        }
    }

    public void updatePriceCatalog(List<Double> newPrices6m, List<Double> newPrices12m) {
        HashMap<MemberType, Double> prices6m = createPrices(newPrices6m, 6);
        HashMap<MemberType, Double> prices12m = createPrices(newPrices12m, 12);
        int id = library.getPriceCatalogs().size() + 1;
        PriceCatalog newPriceCatalog = new PriceCatalog(id, LocalDate.now(), prices6m, prices12m);
        PriceCatalog currentCatalog = library.getCurrentCatalog();
        currentCatalog.setToDate(LocalDate.now());
        libraryRepo.updateToDatePriceCatalog(currentCatalog);
        library.setNewPriceCatalog(newPriceCatalog);
        libraryRepo.addPriceCatalog(newPriceCatalog);
        libraryRepo.addHalfAYearPrices(newPriceCatalog);
        libraryRepo.addFullYearPrices(newPriceCatalog);
    }

    private HashMap<MemberType, Double> createPrices(List<Double> newPrices, int numOfMonths) {
        HashMap<MemberType, Double> prices = new HashMap<>();
        addPrice(prices, MemberType.REGULAR, newPrices.get(0), numOfMonths);
        addPrice(prices, MemberType.STUDENT, newPrices.get(1), numOfMonths);
        addPrice(prices, MemberType.PRESCHOOLER, newPrices.get(2), numOfMonths);
        addPrice(prices, MemberType.PUPIL, newPrices.get(3), numOfMonths);
        addPrice(prices, MemberType.RETIRED, newPrices.get(4), numOfMonths);
        addPrice(prices, MemberType.PRIVILEGED, newPrices.get(5), numOfMonths);

        return prices;
    }

    private void addPrice(Map<MemberType, Double> prices, MemberType type, Double newPrice, int numOfMonths) {
        double price = getPrice(type, newPrice, numOfMonths);
        prices.put(type, price);
    }

    private double getPrice(MemberType type, Double newPrice, int numOfMonths) {

        if (newPrice == null) {
            if (library.getPriceCatalogs().size() != 0) {
                newPrice = library.getCurrentCatalog().getPrice(type, numOfMonths);
            } else { // first time adding prices
                newPrice = 0.0;
            }
        }

        return newPrice;
    }

}
