package model;

import java.time.LocalDate;
import java.util.List;

public class Librarian extends Person{
    private List<IssuedBook> issuedBooks;

    public Librarian(String name, String surname, String jmbg, String phoneNumber, LocalDate birthDate, Object o) {
        super();
    }
}
