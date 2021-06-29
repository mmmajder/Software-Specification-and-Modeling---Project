package model;

import java.time.LocalDate;
import java.util.List;

import model.enums.MemberType;

public class Member extends Person {

    private MemberType type;
    private List<Payment> payments;
    private List<IssuedBook> returnedBooks;
    private List<IssuedBook> currentlyTaken;

    public Member(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, MemberType type) {
        super(name, surname, JMBG, phoneNumber, birthDate);
        this.type = type;
    }

    public Member(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, MemberType type, Account account) {
        super(name, surname, JMBG, phoneNumber, birthDate, account);
        this.type = type;
    }

    public void setType(MemberType type) {
        this.type = type;
    }

    public MemberType getType() {
        return type;
    }
}
