package model;

import model.enums.MemberType;
import observer.Observer;
import observer.Publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library implements Publisher {

    private List<Account> accounts;
    private List<Payment> payments;
    private PriceCatalog currentCatalog;
    private List<PriceCatalog> priceCatalogs;
    private HashMap<MemberType, Integer> maxIssueDays;
    private HashMap<MemberType, Integer> maxIssuedBooks;
    private List<BookSection> sections;

    private List<Observer> observers;

    public Library() {
        accounts = new ArrayList<>();
        payments = new ArrayList<>();
        priceCatalogs = new ArrayList<>();
        maxIssueDays = new HashMap<>();
        maxIssuedBooks = new HashMap<>();
        sections = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }
    
    public void addAccount(Account account) {
        accounts.add(account);
    }

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
