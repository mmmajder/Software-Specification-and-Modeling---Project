package controller;

import model.Library;
import model.PriceCatalog;
import model.enums.MemberType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminSettingsControllerTest {

    @Test
    void updateMaxIssueDaysTrue() {
        Library library = new Library();
        HashMap <MemberType, Integer> maxIssueDays = getTestMaxNums();
        library.setMaxIssueDays(maxIssueDays);
        AdminSettingsController adminSettingsController = new AdminSettingsController(library);
        adminSettingsController.updateMaxIssueDays(getTestNewNumbers());

        assertTrue(library.getMaxIssueDays(MemberType.STUDENT) == 5);
    }

    @Test
    void updateMaxIssueDaysFalse() {
        Library library = new Library();
        HashMap <MemberType, Integer> maxIssueDays = getTestMaxNums();
        library.setMaxIssueDays(maxIssueDays);
        AdminSettingsController adminSettingsController = new AdminSettingsController(library);
        adminSettingsController.updateMaxIssueDays(getTestNewNumbers());

        assertFalse(library.getMaxIssueDays(MemberType.STUDENT) == 0);
    }

    @Test
    void updateMaxIssuedBooksTrue() {
        Library library = new Library();
        HashMap <MemberType, Integer> maxIssuedBooks = getTestMaxNums();
        library.setMaxIssuedBooks(maxIssuedBooks);
        AdminSettingsController adminSettingsController = new AdminSettingsController(library);
        adminSettingsController.updateMaxIssuedBooks(getTestNewNumbers());

        assertTrue(library.getMaxIssuedBooks(MemberType.STUDENT) == 5);
    }

    @Test
    void updateMaxIssuedBooksFalse() {
        Library library = new Library();
        HashMap <MemberType, Integer> maxIssuedBooks = getTestMaxNums();
        library.setMaxIssuedBooks(maxIssuedBooks);
        AdminSettingsController adminSettingsController = new AdminSettingsController(library);
        adminSettingsController.updateMaxIssuedBooks(getTestNewNumbers());

        assertFalse(library.getMaxIssuedBooks(MemberType.STUDENT) == 0);
    }

    @Test
    void updatePriceCatalogHalfYearTrue() {
        Library library = new Library();
        HashMap<MemberType, Double> prices = getTestPrices();
        PriceCatalog priceCatalog = new PriceCatalog();
        priceCatalog.setHalfAYearPrices(prices);
        AdminSettingsController adminSettingsController = new AdminSettingsController(library);
        adminSettingsController.updatePriceCatalog(getTestNewPrices(), getTestNewPrices());

        assertTrue(library.getHalfAYearPrice(MemberType.STUDENT) == 500.0);
    }

    @Test
    void updatePriceCatalogFullYearTrue() {
        Library library = new Library();
        HashMap<MemberType, Double> prices = getTestPrices();
        PriceCatalog priceCatalog = new PriceCatalog();
        priceCatalog.setHalfAYearPrices(prices);
        AdminSettingsController adminSettingsController = new AdminSettingsController(library);
        adminSettingsController.updatePriceCatalog(getTestNewPrices(), getTestNewPrices());

        assertTrue(library.getFullYearPrice(MemberType.STUDENT) == 500.0);
    }

    @Test
    void updatePriceCatalogHalfYearFalse() {
        Library library = new Library();
        HashMap<MemberType, Double> prices = getTestPrices();
        PriceCatalog priceCatalog = new PriceCatalog();
        priceCatalog.setHalfAYearPrices(prices);
        AdminSettingsController adminSettingsController = new AdminSettingsController(library);
        adminSettingsController.updatePriceCatalog(getTestNewPrices(), getTestNewPrices());

        assertFalse(library.getHalfAYearPrice(MemberType.STUDENT) == 0.0);
    }

    @Test
    void updatePriceCatalogFullYearFalse() {
        Library library = new Library();
        HashMap<MemberType, Double> prices = getTestPrices();
        PriceCatalog priceCatalog = new PriceCatalog();
        priceCatalog.setHalfAYearPrices(prices);
        AdminSettingsController adminSettingsController = new AdminSettingsController(library);
        adminSettingsController.updatePriceCatalog(getTestNewPrices(), getTestNewPrices());

        assertFalse(library.getFullYearPrice(MemberType.STUDENT) == 0.0);
    }

    private List<Integer> getTestNewNumbers(){
        List<Integer> numbers = new ArrayList<>();
        numbers.add(null);
        numbers.add(5);
        numbers.add(4);
        numbers.add(null);
        numbers.add(null);

        return numbers;
    }

    private HashMap<MemberType, Integer> getTestMaxNums(){
        HashMap <MemberType, Integer> maxNums = new HashMap<>();
        maxNums.put(MemberType.REGULAR, 0);
        maxNums.put(MemberType.STUDENT, 0);
        maxNums.put(MemberType.PRESCHOOLER, 0);
        maxNums.put(MemberType.PUPIL, 0);
        maxNums.put(MemberType.RETIRED, 0);

        return maxNums;
    }

    private List<Double> getTestNewPrices(){
        List<Double> prices = new ArrayList<>();
        prices.add(null);
        prices.add(500.0);
        prices.add(400.0);
        prices.add(null);
        prices.add(null);

        return prices;
    }

    private HashMap<MemberType, Double> getTestPrices(){
        HashMap <MemberType, Double> prices = new HashMap<>();
        prices.put(MemberType.REGULAR, 100.0);
        prices.put(MemberType.STUDENT, 100.0);
        prices.put(MemberType.PRESCHOOLER, 100.0);
        prices.put(MemberType.PUPIL, 100.0);
        prices.put(MemberType.RETIRED, 100.0);

        return prices;
    }
}