package controller;

import model.Account;
import model.IssuedBook;
import model.Library;
import model.Member;
import model.enums.AccountType;
import model.enums.BookState;
import model.enums.IssuedBookState;
import utils.exceptions.PersonIsNotAMemberException;

import java.time.LocalDate;
import java.util.List;

public class IssuedBookController {

    private Library library;

    public IssuedBookController(Library library) {
        this.library = library;
    }

    public List<IssuedBook> getMembersReturnedBooks(Account account) throws PersonIsNotAMemberException {
        if (account.getType() != AccountType.MEMBER) {
            throw new PersonIsNotAMemberException();
        }

        return library.getMembersReturnedBooks(account);
    }

    public LocalDate calculateReturnDate(IssuedBook issuedBook){
        int maxIssueDays = library.getMaxIssueDays(issuedBook.getMember().getType());

        if (issuedBook.isProlongedIssue()){
            maxIssueDays *= 2;
        }

        return issuedBook.getIssueDate().plusDays(maxIssueDays);
    }

}
