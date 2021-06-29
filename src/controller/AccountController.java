package controller;

import model.Account;
import model.Library;
import utils.exceptions.InvalidAccountPassword;
import utils.exceptions.NoAccountWithThatUsername;

public class AccountController {

    private Library library;

    public AccountController(Library library) {
        this.library = library;
    }

    public boolean usernameExists(String username) throws NoAccountWithThatUsername {
        for (Account account : library.getAccounts()) {
            if (account.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        throw new NoAccountWithThatUsername();
    }

    public boolean passwordValid(Account account, String password) throws InvalidAccountPassword {

        if (account.getPassword().equals(password)){
            return true;
        }

        throw new InvalidAccountPassword();
    }
}
