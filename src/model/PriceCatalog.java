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

    public PriceCatalog(int catalogId, LocalDate fromDate, LocalDate toDate) {
        this.catalogId = catalogId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.halfAYearPrices = new HashMap<>();
        this.fullYearPrices = new HashMap<>();
    }

    public HashMap<MemberType, Double> getHalfAYearPrices() {
        return halfAYearPrices;
    }

    public HashMap<MemberType, Double> getFullYearPrices() {
        return fullYearPrices;
    }

    public double getPrice(MemberType type, int numOfMonths) {
        if (numOfMonths == 6) {
            return halfAYearPrices.get(type);
        }

        return fullYearPrices.get(type);
    }
}
