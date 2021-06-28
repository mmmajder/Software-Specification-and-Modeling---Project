package model;

import model.enums.MemberType;
import observer.Observer;
import observer.Publisher;
import utils.exceptions.MissingValueException;
import utils.exceptions.NoAccountWithThatUsername;

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

    private ArrayList<Edition> editions;

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

    public boolean doesEditionIdExist(String editionId){
        for (Edition e : editions){
            if (e.getEditionId().equals(editionId)){
                return true;
            }
        }

        return false;
    }
    public Account getAccount(String username) throws NoAccountWithThatUsername {
        for (Account a : accounts){
            if (a.getUsername().equals(username)){
                return a;
            }
        }

        throw new NoAccountWithThatUsername();
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
