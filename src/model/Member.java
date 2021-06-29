package model;

import java.time.LocalDate;
import java.util.List;

import model.enums.MemberType;

public class Member extends Person {
    private MemberType type;
    private List<Payment> payments;
    private List<IssuedBook> returnedBooks;
    private List<IssuedBook> currentlyTaken;

    public Member(String name, String surname, String jmbg, String phoneNumber, LocalDate birthDate, Object o) {
        super();
    }
}
