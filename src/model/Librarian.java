package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Librarian extends Person {

    private List<IssuedBook> issuedBooks;

    public Librarian() {
        super();
        this.issuedBooks = new ArrayList<>();
    }

    public Librarian(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account) {
        super(name, surname, JMBG, phoneNumber, birthDate, account);
        this.issuedBooks = new ArrayList<>();
    }

    public void addIssuedBook(IssuedBook issuedBook) {
        this.issuedBooks.add(issuedBook);
    }
}
