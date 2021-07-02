package controller;

import model.IssuedBook;
import model.Library;

import java.time.LocalDate;

public class IssuedBookControler {

    private Library library;

    public IssuedBookControler(Library library) {
        this.library = library;
    }

    public LocalDate calculateReturnDate(IssuedBook issuedBook){
        int maxIssueDays = library.getMaxIssueDays(issuedBook.getMember().getType());

        if (issuedBook.isProlongedIssue()){
            maxIssueDays *= 2;
        }

        return issuedBook.getIssueDate().plusDays(maxIssueDays);
    }

}
