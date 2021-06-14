package model;

import enums.MemberType;

import java.time.LocalDate;
import java.util.HashMap;

public class PriceCatalog {

    private int catalogId;
    private LocalDate fromDate;
    private LocalDate toDate = null;
    private HashMap<MemberType, Double> prices;
}
