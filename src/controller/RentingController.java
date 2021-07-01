package controller;

import model.*;
import model.enums.SampleState;
import utils.exceptions.BookRentingIsInvalidException;
import utils.exceptions.MemberUnableToRentException;

public class RentingController {

    private Library library;

    public RentingController(Library library) {
        this.library = library;
    }

    public void rent(Member member, Book book, Librarian librarian) throws BookRentingIsInvalidException, MemberUnableToRentException {
        validateMemberRent(member);
        validateBookRent(member, book);
        createIssue(member, book, librarian);
    }

    private void validateMemberRent(Member member) throws MemberUnableToRentException {
        if (!member.getIsMembershipPaid()) { throw new MemberUnableToRentException("subscription");}
        if (!isAbleToRent(member)) { throw new MemberUnableToRentException("maxNumberOfTakenBooksReached"); }
    }

    private boolean isAbleToRent(Member member){
        return member.getCurrentlyTakenBooks().size() < library.getMaxIssuedBooks(member.getType());
    }

    private void validateBookRent(Member member, Book book) throws BookRentingIsInvalidException {
        if (!(book.isAvailable() || isReservedForMember(member, book)) || book.getIsRestricted()){
            throw new BookRentingIsInvalidException();
        }
    }

    private boolean isReservedForMember(Member member, Book book){
        return member.getReservedBook().getBook().getBookId().equals(book.getBookId());
    }

    private void createIssue(Member member, Book book, Librarian librarian){
        IssuedBook issuedBook = new IssuedBook(member, book, librarian);
        member.addTakenBook(issuedBook);
        book.addIssueHistory(issuedBook);
        library.addIssuedBook(issuedBook);
        book.setState(SampleState.TAKEN);
    }
}
