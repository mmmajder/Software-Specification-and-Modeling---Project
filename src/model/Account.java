package model;

import model.enums.AccountType;

public class Account {
    private String username;
    private String password;
    private String email;
    private AccountType type;
    private Person person;

    public Account(String username, String password, String email, AccountType type, Object o) {
    }
}
