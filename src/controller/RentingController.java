package controller;

import model.*;
import model.enums.BookState;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.BookNotFoundException;
import utils.exceptions.BookRentingIsInvalidException;
import utils.exceptions.MemberNotFoundException;
import utils.exceptions.MemberUnableToRentException;

public class RentingController {

    private Library library;
    private ILibraryRepo libraryRepo;

    public RentingController(Library library) {
        this.library = library;
        this.libraryRepo = new LibraryRepo();
    }

    public void rent(String jmbg, String bookId, Person person) throws BookRentingIsInvalidException,
            MemberUnableToRentException, MemberNotFoundException, BookNotFoundException {

        Librarian librarian = (Librarian) person;
        Member member = (Member) library.getPerson(jmbg);
        memberExists(member);
        Book book = library.getBook(bookId);
        bookExists(book);
        validateMemberRent(member);
        validateBookRent(member, book);
        createIssue(member, book, librarian);
    }

    private void bookExists(Book book) throws BookNotFoundException {

        if (book == null) {

            throw new BookNotFoundException();
        }
    }

    private void memberExists(Member member) throws MemberNotFoundException {

        if (member == null) {

            throw new MemberNotFoundException();
        }
    }

    private void validateMemberRent(Member member) throws MemberUnableToRentException {
        if (!member.isMembershipPaid()) { throw new MemberUnableToRentException("subscription");}
        if (!isAbleToRent(member)) { throw new MemberUnableToRentException("maxNumberOfTakenBooksReached"); }
    }

    private boolean isAbleToRent(Member member) {
        return member.getCurrentlyTakenBooks().size() < library.getMaxIssuedBooks(member.getType());
    }

    private void validateBookRent(Member member, Book book) throws BookRentingIsInvalidException {
        if (!(book.isAvailable() || isReservedForMember(member, book)) || book.isRestricted()){
            throw new BookRentingIsInvalidException();
        }
    }

    private boolean isReservedForMember(Member member, Book book) {
        return member.getReservedBookId().equalsIgnoreCase(book.getBookId());
    }

    private void createIssue(Member member, Book book, Librarian librarian) {
        IssuedBook issuedBook = new IssuedBook(member, book, librarian);
        member.addTakenBook(issuedBook);
        book.addIssueHistory(issuedBook);
        librarian.addIssuedBook(issuedBook);
        library.addIssuedBook(issuedBook);
        book.setState(BookState.TAKEN);
        libraryRepo.addIssuedBook(issuedBook);
    }
}
