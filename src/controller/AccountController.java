package controller;

import model.Account;
import model.Library;
import model.Member;
import model.Person;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import utils.StringUtils;
import utils.exceptions.InvalidAccountPassword;
import utils.exceptions.NoAccountWithThatUsername;

import java.time.LocalDate;

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

    public boolean passwordValid(Account account, String password) {

        return account.getPassword().equals(password);
    }

    public String getFullName(Person person) {
        return person.getName() + " " + person.getSurname();
    }

    public String getMembershipExpirationDateStr(Person person) {
        Member member = (Member) person;
        LocalDate expirationDate = member.getMembershipExpirationDate();

        if (expirationDate != null) {
            return StringUtils.dateToString(expirationDate, "dd.MM.yyyy.");
        }

        return null;
    }

    public LocalDate getMembershipExpirationDate(Person person) {
        Member member = (Member) person;

        return member.getMembershipExpirationDate();
    }

    public String getMembershipStatus(Account account) {
        Member member = (Member) account.getPerson();
        if (getMembershipExpirationDate(member) == null || getMembershipExpirationDate(member).isBefore(LocalDate.now())) {
            return "Membership status: NOT ACTIVE";
        }
        return "Membership status: ACTIVE UNTIL " + this.getMembershipExpirationDate(account.getPerson());
    }
}