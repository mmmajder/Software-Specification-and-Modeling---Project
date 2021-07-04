package model;

import model.enums.MemberType;

import java.time.LocalDate;
import java.util.HashMap;

public class PriceCatalog {

    private int catalogId;
    private LocalDate fromDate;
    private LocalDate toDate = null;
    private HashMap<MemberType, Double> halfAYearPrices;
    private HashMap<MemberType, Double> fullYearPrices;

    public PriceCatalog(int catalogId, LocalDate fromDate) {
        this.catalogId = catalogId;
        this.fromDate = fromDate;
    }
    public PriceCatalog(int catalogId, LocalDate fromDate, HashMap<MemberType, Double> halfAYearPrices, HashMap<MemberType, Double> fullYearPrices) {
        this(catalogId, fromDate);
        this.toDate = null;
        this.halfAYearPrices = halfAYearPrices;
        this.fullYearPrices = fullYearPrices;
    }

    public PriceCatalog(int catalogId, LocalDate fromDate, LocalDate toDate) {
        this(catalogId, fromDate);
        this.toDate = toDate;
        this.halfAYearPrices = new HashMap<>();
        this.fullYearPrices = new HashMap<>();
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void addHalfAYearPrice(MemberType type, double price) {
        halfAYearPrices.put(type, price);
    }

    public void addFullYearPrice(MemberType type, double price) {
        fullYearPrices.put(type, price);
    }

    public double getPrice(MemberType type, int numOfMonths) {
        if (numOfMonths == 6) {
            return halfAYearPrices.get(type);
        }

        return fullYearPrices.get(type);
    }
}
