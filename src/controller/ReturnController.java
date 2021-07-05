package controller;

import model.Book;
import model.IssuedBook;
import model.Library;
import model.Member;
import utils.exceptions.NoCurrentlyIssuedBookWithThatIdException;

import java.time.LocalDate;

public class ReturnController {
    private Library library;

    public ReturnController(Library library) {
        this.library = library;
    }

    public void returnBook(String bookId) throws NoCurrentlyIssuedBookWithThatIdException {
        IssuedBook issuedBook = getIssue(bookId);
        issuedBook.setReturnDate(LocalDate.now());
        Member member = issuedBook.getMember();
        member.removeTaken(bookId);
        member.addReturnedBook(issuedBook);
        issuedBook.getBook().makeAvailable();
    }

    private IssuedBook getIssue(String bookId) throws NoCurrentlyIssuedBookWithThatIdException {
        for (IssuedBook issuedBook : library.getCurrentlyIssued()){
            if (issuedBook.getBook().getBookId().equals(bookId)){
                return issuedBook;
            }
        }

        throw new NoCurrentlyIssuedBookWithThatIdException();
    }
}
