package controller;

import model.Book;
import model.IssuedBook;
import model.Library;
import model.Member;
import model.enums.SampleState;
import utils.exceptions.BookRentingIsInvalidException;
import utils.exceptions.MemberUnableToRentException;

public class RentingController {

    private Library library;

    public RentingController(Library library) {
        this.library = library;
    }

    public void rent(Member member, Book book) throws BookRentingIsInvalidException, MemberUnableToRentException {
        validateMemberRent(member);
        validateBookRent(member, book);
        createIssue(member, book);
    }

    private void validateMemberRent(Member member) throws MemberUnableToRentException {
        if (!member.getSubscriptionValid()) { throw new MemberUnableToRentException("subscription");}
        if (!member.isAbleToRent()) { throw new MemberUnableToRentException("maxNumberOfTakenBooksReached"); }
    }

    private void validateBookRent(Member member, Book book) throws BookRentingIsInvalidException {
        if (!(book.isAvailable() || isReservedForMember(member, book)) || book.getIsRestricted()){
            throw new BookRentingIsInvalidException();
        }
    }

    private boolean isReservedForMember(Member member, Book book){
        return member.getReservedBookId() == book.getBookId();
    }

    private void createIssue(Member member, Book book){
        IssuedBook issuedBook = new IssuedBook(member, book);
        member.addTakenBook(issuedBook);
        book.addIssueHistory(issuedBook);
        library.addIssuedBook(issuedBook);
        book.setState(SampleState.TAKEN);
    }
}
