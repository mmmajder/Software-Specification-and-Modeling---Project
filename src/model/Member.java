package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.enums.MemberType;
import utils.exceptions.NoCurrentlyIssuedBookWithThatIdException;


public class Member extends Person {

    private MemberType type;
    private double debt;
    private List<Payment> payments;
    private List<IssuedBook> returnedBooks;
    private List<IssuedBook> currentlyTakenBooks;
    private PendingReservation pendingReservation;
    private Reservation reservation;
    private boolean isMembershipPaid;
    private boolean isActive;

    public Member() {
        super();
        this.pendingReservation = null;
        this.reservation = null;
        this.isMembershipPaid = true;
        this.isActive = true;
        this.payments = new ArrayList<>();
        this.returnedBooks = new ArrayList<>();
        this.currentlyTakenBooks = new ArrayList<>();
    }

    public Member(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account,
                  MemberType type, double debt, boolean isMembershipPaid, boolean isActive) {

        super(name, surname, JMBG, phoneNumber, birthDate, account);
        this.type = type;
        this.debt = debt;
        this.isMembershipPaid = isMembershipPaid;
        this.isActive = isActive;
        this.pendingReservation = null;
        this.reservation = null;
        this.isMembershipPaid = true;
        this.isActive = true;
        this.payments = new ArrayList<>();
        this.returnedBooks = new ArrayList<>();
        this.currentlyTakenBooks = new ArrayList<>();
    }

    public boolean isActive() {
        return isActive;
    }

    public double getDebt() {
        return debt;
    }

    public LocalDate getMembershipExpirationDate() {
        LocalDate expirationDate = null;
        if (!payments.isEmpty()) {
            Payment lastPayment = payments.get(payments.size() - 1);
            expirationDate = lastPayment.getValidToDate();
        }

        return expirationDate;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    public String getFullName() {
        return this.getName() + " " + this.getSurname();
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

    public boolean hasAlreadyReserved() {
        return pendingReservation != null && reservation != null;
    }

    public void addReturnedBook(IssuedBook issuedBook) {
        this.returnedBooks.add(issuedBook);
    }

    public List<IssuedBook> getReturnedBooks() {
        return returnedBooks;
    }

    public void addTakenBook(IssuedBook issuedBook) {
        this.currentlyTakenBooks.add(issuedBook);
    }

    public Book getReservedBook() {
        return reservation.getBook();
    }

    public String getReservedBookId() {
        return this.reservation.getBook().getBookId();
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void removePendingReservation() {
        this.pendingReservation = null;
    }

    public void removeReservation() {
        this.reservation = null;
    }

    public PendingReservation getPendingReservation() {
        return pendingReservation;
    }

    public void setPendingReservation(PendingReservation pendingReservation) {
        this.pendingReservation = pendingReservation;
    }

    public List<IssuedBook> getCurrentlyTakenBooks() {
        return currentlyTakenBooks;
    }

    public Payment getLastPayment() {
        return payments.get(payments.size() - 1);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public void removeTaken(String bookId) throws NoCurrentlyIssuedBookWithThatIdException {
        boolean removed = false;

        for (int i = 0; i < currentlyTakenBooks.size(); i++){
            IssuedBook issuedBook = currentlyTakenBooks.get(i);

            if (issuedBook.getBook().getBookId().equals(bookId)){
                currentlyTakenBooks.remove(i);
                removed = true;
                break;
            }
        }

        if (!removed) { throw new NoCurrentlyIssuedBookWithThatIdException(); }
    }
}
