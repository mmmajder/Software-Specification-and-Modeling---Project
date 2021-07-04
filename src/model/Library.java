package model;

import model.enums.AccountType;
import model.enums.BookState;
import model.enums.MemberType;
import observer.Observer;
import observer.Publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private List<Reservation> reservations;
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
        this.reservations = new ArrayList<>();
        this.currentlyIssued = new ArrayList<>();
        this.formats = new ArrayList<>();
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Contributor> getContributors() {
        return contributors;
    }

    public Contributor fromFullName(String fullName) {
        for (Contributor contributor : getContributors()) {
            if (fullName.equals(contributor.getFullName())) {
                return contributor;
            }
        }
        return null;
    }

    public Genre fromName(String name) {
        for (Genre genre : getGenres()) {
            if (name.equals(genre.getName())) {
                return genre;
            }
        }
        return null;
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

    public void setMaxIssueDays(MemberType type, int newMaxIssueDays) { this.maxIssueDays.put(type, newMaxIssueDays); }

    public void setMaxIssuedBooks(HashMap<MemberType, Integer> maxIssuedBooks) {
        this.maxIssuedBooks = maxIssuedBooks;
    }

    public void setMaxIssuedBooks(MemberType type, int newMaxIssuedBooks) { this.maxIssuedBooks.put(type, newMaxIssuedBooks); }

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

    public void setAccounts(List<Account> accounts) {this.accounts = accounts; }

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
            if (edition.getEditionId().equals(editionId)) {
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

    public void addIssueDayConstraint(MemberType type, int days) {
        this.maxIssueDays.put(type, days);
    }

    public void addIssuedBooksConstraint(MemberType type, int limit) {
        this.maxIssuedBooks.put(type, limit);
    }

    public List<IssuedBook> getMembersCurrentlyTakenBooks(String jmbg) {
        Member member = (Member) getPerson(jmbg);
        return member.getCurrentlyTakenBooks();
    }

    public Account getAccountByUsername(String username) {
        for (Account account : this.accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    public Account getAccountByEmail(String email) {

        for (Account account : this.accounts) {
            if (account.getEmail().equals(email)) {
                return account;
            }
        }

        return null;
    }

    public Person getPerson(String jmbg) {
        for (Person person : this.persons) {
            if (person.getJMBG().equals(jmbg)) {
                return person;
            }
        }
        return null;
    }

    public void addPendingReservation(PendingReservation pendingReservation) {
        this.pendingReservations.add(pendingReservation);
    }

    public List<PendingReservation> getPendingReservations() {
        return pendingReservations;
    }


    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }


    public List<Reservation> getReservations() {
        return reservations;
    }


    public Book getBook(String bookId) {

        for (Book book : this.books) {

            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }

        return null;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addCatalog(PriceCatalog catalog) {
        this.priceCatalogs.add(catalog);
    }

    public void setCurrentPriceCatalog() {
        int indexOfLastCatalog = this.priceCatalogs.size() - 1;
        this.currentCatalog = this.priceCatalogs.get(indexOfLastCatalog);
    }

    public void setNewPriceCatalog(PriceCatalog catalog){
        priceCatalogs.add(catalog);
        currentCatalog = catalog;
    }

    public List<PriceCatalog> getPriceCatalogs() { return priceCatalogs; }

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

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getType() == AccountType.MEMBER) {
                members.add((Member) account.getPerson());
            }
        }

        for (Person person : persons) {

            if (person.getAccount() == null) {

                members.add((Member) person);
            }
        }

        return members;
    }

    public boolean isAvailable(Edition edition) {

        for (Book book : edition.getBooks()) {

            if (book.getState() == BookState.AVAILABLE) {

                return true;
            }
        }

        return false;
    }

    public List<Book> getAvailableBooks(Edition edition) {
        List<Book> availableBooks = new ArrayList<>();

        for (Book book : edition.getBooks()) {
            if (book.getState() == BookState.AVAILABLE) {
                availableBooks.add(book);
            }
        }

        return availableBooks;
    }

    public Book getAvailableBook(Edition edition) {

        for (Book book : edition.getBooks()) {

            if (book.getState() == BookState.AVAILABLE) {

                return book;
            }
        }

        return null;
    }

    public List<IssuedBook> getMembersReturnedBooks(Account account) {
        Member member = (Member) account.getPerson();
        return member.getReturnedBooks();
    }

    public List<IssuedBook> getMembersCurrentlyTakenBooks(Account account) {
        Member member = (Member) account.getPerson();
        return member.getCurrentlyTakenBooks();
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    public int getMaxNumberOfTakenBooks(MemberType memberType) {
        return this.maxIssuedBooks.get(memberType);
    }

    public int getMaxNumberOfIssueDays(MemberType memberType) {
        return this.maxIssueDays.get(memberType);
    }

    public double getHalfAYearPrice(MemberType memberType) {
        return this.currentCatalog.getPrice(memberType, 6);
    }

    public double getFullYearPrice(MemberType memberType) {
        return this.currentCatalog.getPrice(memberType, 12);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void addFormat(BookFormat format) {
        formats.add(format);
    }

    public List<BookFormat> getFormats() {
        return formats;
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }

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
}
