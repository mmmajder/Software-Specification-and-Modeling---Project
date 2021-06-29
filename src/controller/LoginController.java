package controller;

import model.Account;
import model.Library;
import services.AccountService;
import services.LibraryService;
import utils.StringUtils;
import utils.exceptions.InvalidAccountPassword;
import utils.exceptions.MissingValueException;
import utils.exceptions.NoAccountWithThatUsername;

import java.util.ArrayList;

public class LoginController {

    private ArrayList<Account> accounts;

    public LoginController(ArrayList<Account> accounts){
        this.accounts = accounts;
    }

    public void login(String username, String password) throws MissingValueException, NoAccountWithThatUsername, InvalidAccountPassword {
        validateInputValues(username, password);
        Account a = AccountService.getAccount(accounts, username, password);
        //TODO what should be done with the Account a?
    }

    private void validateInputValues(String username, String password) throws MissingValueException {
        if (StringUtils.isNullOrEmpty(username)) {
            throw new MissingValueException("username");
        }
        if (StringUtils.isNullOrEmpty(password)) {
            throw new MissingValueException("password");
        }
    }
}
