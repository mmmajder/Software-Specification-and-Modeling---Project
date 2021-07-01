package model;

import model.enums.AccountType;
import utils.StringUtils;

import java.time.LocalDate;

public class Account {
    private String username;
    private String password;
    private String email;
    private AccountType type;
    private Person person;
    private boolean isActive;

    public Account(String username, String password, String email, AccountType type, Person person, boolean isActive) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
        this.person = person;
        this.isActive = isActive;
    }

    public String getMembershipExpirationDateStr() {
        Member member = (Member) person;
        LocalDate expirationDate = member.getMembershipExpirationDate();

        if (expirationDate != null) {
            return StringUtils.dateToString(expirationDate, "dd.mm.yyyy.");
        }

        return null;
    }


    public Person getPerson() {
        return person;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getType() {
        return type;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getFullName() {
        return this.person.getName() + " " + this.person.getSurname();
    }
}
