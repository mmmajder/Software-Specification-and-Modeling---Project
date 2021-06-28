package controller;

import model.Account;
import model.Library;
import services.LibraryService;
import utils.StringUtils;
import utils.exceptions.MissingValueException;

public class LoginController {

    public void login(String username, String password) throws MissingValueException {
        //TODO Library l
        if (StringUtils.isNullOrEmpty(username)) {
            throw new MissingValueException("username");
        }
        else if (StringUtils.isNullOrEmpty(password)) {
            throw new MissingValueException("password");
        }

        //Account a = LibraryService.getAccount(l, username, password);
        //TODO what should be done with the Account a?
    }
}
