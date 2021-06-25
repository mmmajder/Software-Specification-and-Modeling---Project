package model;

import java.time.LocalDate;

public class Admin extends Librarian {

    public Admin(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account) {
        super(name, surname, JMBG, phoneNumber, birthDate, account);
    }
}
