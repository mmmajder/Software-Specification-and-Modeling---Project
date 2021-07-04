package controller;

import model.IssuedBook;
import model.Library;

import java.time.LocalDate;

public class IssuedBookController {

    private Library library;

    public IssuedBookController(Library library) {
        this.library = library;
    }

    public String getIssuedBookState(IssuedBook issuedBook) {

        if (bookReturned(issuedBook)) {
            return "RETURNED";
        }

        return "TAKEN";
    }

    private boolean bookReturned(IssuedBook issuedBook) {
        return issuedBook.getReturnDate() == null;
    }

    public LocalDate calculateReturnDate(IssuedBook issuedBook) {
        int maxIssueDays = library.getMaxIssueDays(issuedBook.getMember().getType());
        System.out.println(maxIssueDays);
        if (issuedBook.isProlongedIssue()) {
            maxIssueDays *= 2;
        }
        System.out.println(maxIssueDays);
        System.out.println(issuedBook.getIssueDate().plusDays(maxIssueDays));
        System.out.println("-----------------------------------------");
        return issuedBook.getIssueDate().plusDays(maxIssueDays);
    }

    public String getAuthorName(IssuedBook issuedBook) {
        return issuedBook.getBook().getEdition().getAuthor().getName() + " " + issuedBook.getBook().getEdition().getAuthor().getSurname();
    }


}
