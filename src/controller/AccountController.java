package controller;

import model.Account;
import model.Library;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import utils.exceptions.InvalidAccountPassword;
import utils.exceptions.NoAccountWithThatUsername;

public class AccountController {

    private Library library;

    public AccountController(Library library) {
        this.library = library;
    }

    public Account getAccount(String username, String password) throws NoAccountWithThatUsername, InvalidAccountPassword {

        if (usernameExists(username)) {
            Account account = library.getAccountByUsername(username);

            if (passwordValid(account, password)) {
                return account;
            }
            throw new InvalidAccountPassword();
        }
        throw new NoAccountWithThatUsername();
    }

    public boolean usernameExists(String username) {

        for (Account account : library.getAccounts()) {
            if (account.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean passwordValid(Account account, String password) throws InvalidAccountPassword {

        return account.getPassword().equals(password);
    }
}
