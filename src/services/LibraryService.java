package services;

import model.Account;
import model.Library;
import utils.exceptions.InvalidAccountPassword;
import utils.exceptions.NoAccountWithThatUsername;

public class LibraryService {

    static public Account getAccount(Library l, String username, String password) throws NoAccountWithThatUsername, InvalidAccountPassword {
        Account a = l.getAccount(username);

        if (a.getPassword().equals(password)){
            return a;
        }

        throw new InvalidAccountPassword();
    }
}
