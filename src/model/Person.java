package model;

import java.time.LocalDate;

public abstract class Person {

    private String name;
    private String surname;
    private String JMBG;
    private String phoneNumber;
    private LocalDate birthDate;
    private Account account;

    public Person(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account) {
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.account = account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
