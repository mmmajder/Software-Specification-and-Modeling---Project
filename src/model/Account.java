package model;

import model.enums.AccountType;

public class Account {

    private String username;
    private String password;
    private String email;
    private AccountType type;
    private Person person;

    public Account(String username, String password, String email, AccountType type, Person person) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.type = type;
        this.person = person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
