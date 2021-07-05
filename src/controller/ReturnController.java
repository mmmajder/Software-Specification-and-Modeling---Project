package controller;

import model.IssuedBook;
import model.Library;
import model.Member;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.NoCurrentlyIssuedBookWithThatIdException;

import java.time.LocalDate;

public class ReturnController {

    private Library library;
    private ILibraryRepo libraryRepo;

    public ReturnController(Library library) {
        this.library = library;
        this.libraryRepo = new LibraryRepo();
    }

    public void returnBook(String bookId) throws NoCurrentlyIssuedBookWithThatIdException {
        IssuedBook issuedBook = getIssue(bookId);
        issuedBook.setReturnDate(LocalDate.now());
        Member member = issuedBook.getMember();
        member.removeTakenBook(issuedBook);
        member.addReturnedBook(issuedBook);
        issuedBook.getBook().makeAvailable();
        libraryRepo.updateBookState(issuedBook.getBook());
        libraryRepo.updateIssuedBook(issuedBook);
        library.notifyObservers();
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
