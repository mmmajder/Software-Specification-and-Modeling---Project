package model;

import java.time.LocalDate;
import java.util.List;

public class Librarian extends Person {

    private List<IssuedBook> issuedBooks;

    public Librarian(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account) {
        super(name, surname, JMBG, phoneNumber, birthDate, account);
    }
}
