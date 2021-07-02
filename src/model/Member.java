package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.enums.MemberType;


public class Member extends Person {

    private MemberType type;
    private double debt;
    private List<Payment> payments;
    private List<IssuedBook> returnedBooks;
    private List<IssuedBook> currentlyTakenBooks;
    private PendingReservation pendingReservation;
    private ReservedBook reservedBook;
    private boolean isMembershipPaid;
    private boolean isActive;
    private List<Notification> notifications;

    public Member() {
        super();
        this.pendingReservation = null;
        this.reservedBook = null;
        this.isMembershipPaid = true;
        this.isActive = true;
        this.payments = new ArrayList<>();
        this.returnedBooks = new ArrayList<>();
        this.currentlyTakenBooks = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public Member(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account,
                  MemberType type, double debt, boolean isMembershipPaid, boolean isActive) {

        super(name, surname, JMBG, phoneNumber, birthDate, account);
        this.type = type;
        this.debt = debt;
        this.isMembershipPaid = isMembershipPaid;
        this.isActive = isActive;
        this.pendingReservation = null;
        this.reservedBook = null;
        this.isMembershipPaid = true;
        this.isActive = true;
        this.payments = new ArrayList<>();
        this.returnedBooks = new ArrayList<>();
        this.currentlyTakenBooks = new ArrayList<>();
        this.notifications = new ArrayList<>();
    }

    public void requestReservation(PendingReservation pendingReservation) {
        this.pendingReservation = pendingReservation;
    }

    public LocalDate getMembershipExpirationDate() {
        LocalDate expirationDate = null;
        if (!payments.isEmpty()) {
            Payment lastPayment = payments.get(payments.size() - 1);
            expirationDate = lastPayment.getValidToDate();
        }

        return expirationDate;
    }

    public ReservedBook getReservation() {
        return this.reservedBook;
    }

    public void reserveBook(ReservedBook reservedBook) {
        this.reservedBook = reservedBook;
    }

    public boolean isMembershipPaid() {
        return isMembershipPaid;
    }

    public MemberType getType() {
        return type;
    }

    public void addPayment(Payment p) {
        this.payments.add(p);
    }

    public void extendMembership() {
        this.isMembershipPaid = true;
    }

    public void cancelMembership() {
        this.isMembershipPaid = false;
    }

    public boolean getIsMembershipPaid() {
        return this.isMembershipPaid;
    }

    public boolean hasAlreadyReserved() {
        return pendingReservation != null && reservedBook != null;
    }

    public void addReturnedBook(IssuedBook issuedBook) {
        this.returnedBooks.add(issuedBook);
    }

    public List<IssuedBook> getReturnedBooks() { return returnedBooks; }

    public void addTakenBook(IssuedBook issuedBook) {
        this.currentlyTakenBooks.add(issuedBook);
    }

    public Book getReservedBook() {
        return reservedBook.getBook();
    }

    public String getReservedBookId() {
        return this.reservedBook.getBook().getBookId();
    }

    public void setReservedBook(ReservedBook reservedBook){
        this.reservedBook = reservedBook;
    }

    public void removePendingReservation() { this.pendingReservation = null; }

    public void removeReservedBook() { this.reservedBook = null; }

    public PendingReservation getPendingReservation() {
        return pendingReservation;
    }

    public void setPendingReservation(PendingReservation pendingReservation){
        this.pendingReservation = pendingReservation;
    }

    public List<IssuedBook> getCurrentlyTakenBooks() {
        return currentlyTakenBooks;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

    public Payment getLastPayment() { return payments.get(payments.size()-1); }

    public List<Payment> getPayments() { return payments; }
}
