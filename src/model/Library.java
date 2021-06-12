package model;

import observer.Observer;
import observer.Publisher;

import java.util.List;

public class Library implements Publisher {

    private PriceCatalog currentCatalog;
    private List<PriceCatalog> priceCatalogs;
    private HashMap<MemberType, Integer> maxIssueDays;
    private HashMap<MemberType, Integer> maxIssuedBooks;
    private List<BookSection> sections;

    private List<Observer> observers;

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObserver() {

    }
}
