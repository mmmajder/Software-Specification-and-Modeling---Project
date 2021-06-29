package services;

import model.Account;
import model.Library;
import utils.exceptions.InvalidAccountPassword;
import utils.exceptions.NoAccountWithThatUsername;

import java.util.ArrayList;

public class AccountService {

    static public Account getAccount(ArrayList<Account> accounts, String username, String password) throws NoAccountWithThatUsername, InvalidAccountPassword {
        Account a = getAccount(accounts, username);

        if (a.getPassword().equals(password)){
            return a;
        }

        throw new InvalidAccountPassword();
    }

    static public Account getAccount(ArrayList<Account> accounts, String username) throws NoAccountWithThatUsername {
        for (Account a : accounts){
            if (a.getUsername().equals(username)){
                return a;
            }
        }

        throw new NoAccountWithThatUsername();
    }
}