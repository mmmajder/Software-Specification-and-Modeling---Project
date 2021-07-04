package controller;

import model.Account;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountControllerTest {

    @Test
    void usernameExists() {
        boolean answer = true;
        String username = "username1";
        List<Account> accounts = getTestAccounts();
        exists = user
        assertEquals(answer);
    }

    private List<Account> getTestAccounts(){
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            accounts.add(new Account("username" + (i+1), "password" + (i+1)));
        }

        return accounts;
    }

    @Test
    void passwordValid() {
    }

    @Test
    void getMembershipExpirationDate() {
    }
}