package controller;

import model.Account;
import utils.StringUtils;
import utils.exceptions.MissingValueException;

public class LoginController {

    public void login(String username, String password) throws MissingValueException {
        if (StringUtils.isNullOrEmpty(username)) {
            throw new MissingValueException("username");
        }
        else if (StringUtils.isNullOrEmpty(password)) {
            throw new MissingValueException("password");
        }

        //TODO ask for account from repo
    }
}
