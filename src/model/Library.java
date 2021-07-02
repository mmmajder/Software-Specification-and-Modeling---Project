package model;

import model.enums.MemberType;
import observer.Observer;
import observer.Publisher;
import utils.exceptions.NoSuchPendingRequestException;
import utils.exceptions.PersonIsNotAMemberException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Library implements Publisher {

    private List<Person> persons;
    private List<Book> books;
    private List<Edition> editions;
    private List<Genre> genres;
    private List<Contributor> contributors;
    private List<Account> accounts;
    private List<Payment> payments;
    private PriceCatalog currentCatalog;
    private List<PriceCatalog> priceCatalogs;
    private HashMap<MemberType, Integer> maxIssueDays;
    private HashMap<MemberType, Integer> maxIssuedBooks;
    private List<BookSection> sections;
    private List<IssuedBook> currentlyIssued;
    private List<PendingReservation> pendingReservations;
    private List<ReservedBook> reservedBooks;
    private List<BookFormat> formats;

    private List<Observer> observers;

    public Library() {
        this.persons = new ArrayList<>();
        this.books = new ArrayList<>();
        this.genres = new ArrayList<>();
        this.contributors = new ArrayList<>();
        this.editions = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.priceCatalogs = new ArrayList<>();
        this.maxIssueDays = new HashMap<>();
        this.maxIssuedBooks = new HashMap<>();
        this.sections = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.pendingReservations = new ArrayList<>();
        this.reservedBooks = new ArrayList<>();
        this.currentlyIssued = new ArrayList<>();
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Edition> getEditions() {
        return editions;
    }

    public HashMap<MemberType, Integer> getMaxIssuedBooks() {
        return maxIssuedBooks;
    }

    public int getMaxIssuedBooks(MemberType type) {
        return maxIssuedBooks.get(type);
    }

    public HashMap<MemberType, Integer> getMaxIssueDays() {
        return maxIssueDays;
    }

    public int getMaxIssueDays(MemberType type) {
        return maxIssueDays.get(type);
    }

    public void setMaxIssueDays(HashMap<MemberType, Integer> maxIssueDays) {
        this.maxIssueDays = maxIssueDays;
    }

    public void setMaxIssuedBooks(HashMap<MemberType, Integer> maxIssuedBooks) {
        this.maxIssuedBooks = maxIssuedBooks;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public PriceCatalog getCurrentCatalog() {
        return this.currentCatalog;
    }

    public void addPerson(Person person) {
        this.persons.add(person);
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

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void addIssuedBook(IssuedBook issuedBook) {
        this.currentlyIssued.add(issuedBook);
    }

    public List<IssuedBook> getCurrentlyIssued() {
        return currentlyIssued;
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

    public void addIssueDayConstraint (MemberType type, int days) {
        this.maxIssueDays.put(type, days);
    }

    public void addIssuedBooksConstraint(MemberType type, int limit) {
        this.maxIssuedBooks.put(type, limit);
    }

    public List<IssuedBook> getActiveIssues(String jmbg){
        return currentlyIssued.stream().filter(issuedBook -> issuedBook.getMember().getJMBG() == jmbg).collect(Collectors.toList());
    }

    public Account getAccountByUsername(String username) {
        for (Account account : this.accounts) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return account;
            }
        }
        return null;
    }

    public Account getAccountByEmail(String email) {

        for (Account account : this.accounts) {
            if (account.getEmail().equalsIgnoreCase(email)) {
                return account;
            }
        }

        return null;
    }

    public Person getPerson(String jmbg) {
        for (Person person : this.persons) {
            if (person.getJMBG().equalsIgnoreCase(jmbg)) {
                return person;
            }
        }
        return null;
    }

    public void addPendingReservation(PendingReservation pendingReservation) {
        this.pendingReservations.add(pendingReservation);
    }

    public List<PendingReservation> getPendingReservations(){ return  pendingReservations; }



    public void addReservedBook(ReservedBook reservedBook) {
        this.reservedBooks.add(reservedBook);
    }

    public List<ReservedBook> getReservedBooks() { return reservedBooks; }

    public Book getBook(String bookId) {

        for (Book book : this.books) {

            if (book.getBookId().equalsIgnoreCase(bookId)) {
                return book;
            }
        }

        return null;
    }

    public void addCatalog(PriceCatalog catalog) {
        this.priceCatalogs.add(catalog);
    }

    public void setCurrentPriceCatalog() {
        int indexOfLastCatalog = this.priceCatalogs.size() - 1;
        this.currentCatalog = this.priceCatalogs.get(indexOfLastCatalog);
    }

    public PriceCatalog getPriceCatalog(int catalogId) {

        for (PriceCatalog catalog : this.priceCatalogs) {

            if (catalog.getCatalogId() == catalogId) {
                return catalog;
            }
        }

        return null;
    }

    public void removePendingReservation(PendingReservation pendingReservation) {
        this.pendingReservations.remove(pendingReservation);
    }

    public List<IssuedBook> getMemberIssueHistory(Account account) throws PersonIsNotAMemberException {
        if (!(account.getPerson() instanceof Member)){
            throw new PersonIsNotAMemberException();
        }

        return ((Member) account.getPerson()).getReturnedBooks();
    }

    public List<Edition> getEditions(Genre genre){
        return  editions.stream()
                .filter(edition -> edition.getGenres().stream().anyMatch(g -> g.getName() == genre.getName()))
                .collect(Collectors.toList());
    }

    public void addFormat(BookFormat format){ formats.add(format); }

    public List<BookFormat> getFormats() { return formats; }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.updatePerformed();
        }
    }

    //TODO
    public List<Edition> filterEditions(List<Edition> currentEditions, String filter) {
        return currentEditions;
    }
    //TODO
    public List<Edition> filterByGenre(List<Edition> currentEditions, String genreString) {
        return currentEditions;
    }

    //TODO calculate state IssuedBook - getState() - reserved, returned, taken
    //TODO Notification getNotification(Account account)
}
