package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.enums.MemberType;

public class Member extends Person {

    private MemberType type;
    private List<Payment> payments;
    private List<IssuedBook> returnedBooks;
    private List<IssuedBook> currentlyTaken;

    public Member() {
        super();
        this.payments = new ArrayList<>();
        this.returnedBooks = new ArrayList<>();
        this.currentlyTaken = new ArrayList<>();
    }

    public Member(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account) {
        super(name, surname, JMBG, phoneNumber, birthDate, account);
        this.payments = new ArrayList<>();
        this.returnedBooks = new ArrayList<>();
        this.currentlyTaken = new ArrayList<>();
    }

    public MemberType getType() {
        return type;
    }
}
