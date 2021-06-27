package model;

import model.enums.MemberType;
import observer.Observer;
import observer.Publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Library implements Publisher {

    private List<Book> books;
    private List<Edition> editions;
    private List<Contributor> contributors;
    private List<Account> accounts;
    private List<Payment> payments;
    private PriceCatalog currentCatalog;
    private List<PriceCatalog> priceCatalogs;
    private HashMap<MemberType, Integer> maxIssueDays;
    private HashMap<MemberType, Integer> maxIssuedBooks;
    private List<BookSection> sections;

    private List<Observer> observers;

    public Library() {
        this.books = new ArrayList<>();
        this.contributors = new ArrayList<>();
        this.editions = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.priceCatalogs = new ArrayList<>();
        this.maxIssueDays = new HashMap<>();
        this.maxIssuedBooks = new HashMap<>();
        this.sections = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }
    
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void addContributor(Contributor contributor) {
        this.contributors.add(contributor);
    }

    public void addEdition(Edition edition) {
        this.editions.add(edition);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public Edition getEdition(String editionId) {

        for (Edition edition : this.editions) {
            if (edition.getEditionId().equalsIgnoreCase(editionId)) {
                return edition;
            }
        }
        return null;
    }

    public Contributor getContributor(int contributorId) {

        for (Contributor contributor : this.contributors) {
            if (contributor.getContributorId() == contributorId) {
                return contributor;
            }
        }
        return null;
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
