package model;

import java.time.LocalDate;

public class PriceCatalog {

    private int catalogId;
    private LocalDate fromDate;
    private LocalDate toDate = null;
    private HashMap<MemberType, Double> prices;
}
