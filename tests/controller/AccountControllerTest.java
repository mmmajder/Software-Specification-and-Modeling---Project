package controller;

import model.Account;
import model.Library;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountControllerTest {

    @Test
    void usernameExistsTrue() {
        String username = "username1";
        Library library = new Library();
        library.setAccounts(getTestAccounts());
        AccountController accountController = new AccountController(library);

        assertTrue(accountController.usernameExists(username));
    }

    @Test
    void usernameExistsFalse() {
        String username = "usernameeee";
        Library library = new Library();
        library.setAccounts(getTestAccounts());
        AccountController accountController = new AccountController(library);

        assertFalse(accountController.usernameExists(username));
    }

    @Test
    void passwordValidTrue() {
        Account account = new Account("username", "password");
        String password = "password";
        Library library = new Library();
        AccountController accountController = new AccountController(library);

        assertTrue(accountController.passwordValid(account, password));
    }

    @Test
    void passwordValidFalse() {
        Account account = new Account("username", "password");
        String password = "invalid_password";
        Library library = new Library();
        AccountController accountController = new AccountController(library);

        assertFalse(accountController.passwordValid(account, password));
    }

    @Test
    void getMembershipExpirationDate() {
    }

    private List<Account> getTestAccounts(){
        List<Account> accounts = new ArrayList<>();

        for (int i = 1; i <= 3; i++){
            accounts.add(new Account("username" + i, "password" + i));
        }

        return accounts;
    }
}