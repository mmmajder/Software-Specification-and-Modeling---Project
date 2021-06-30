package model;

import java.time.LocalDate;

public abstract class Person {
    private String name;
    private String surname;
    private String JMBG;
    private String phoneNumber;
    private LocalDate birthDate;
    private Account account;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Account getAccount() {
        return account;
    }

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
