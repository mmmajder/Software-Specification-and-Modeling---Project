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
    private List<IssuedBook> currentlyTaken;
    private PendingReservation pendingReservation;
    private ReservedBook reservedBook;
    private boolean isMembershipPaid;
    private boolean isActive;

    public Member() {
        super();
        this.pendingReservation = null;
        this.reservedBook = null;
        this.isMembershipPaid = true;
        this.isActive = true;
        this.payments = new ArrayList<>();
        this.returnedBooks = new ArrayList<>();
        this.currentlyTaken = new ArrayList<>();
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
        this.currentlyTaken = new ArrayList<>();
    }

    public void requestReservation(PendingReservation pendingReservation) {
        this.pendingReservation = pendingReservation;
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

    public boolean hasAlreadyReserved() {
        return pendingReservation != null && reservedBook != null;
    }

    public void addTakenBook(IssuedBook issuedBook) {
        this.currentlyTaken.add(issuedBook);
    }
}
