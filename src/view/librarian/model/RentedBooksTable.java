package view.librarian.model;

import model.enums.BookState;

import java.time.LocalDate;

public class RentedBooksTable {
    private String member;
    private String bookID;
    private String book;
    private LocalDate issuedDate;
    private LocalDate returnDate;
    private BookState state;

    public RentedBooksTable(String member, String bookID, String book, LocalDate issuedDate, LocalDate returnDate, BookState state) {
        this.member = member;
        this.bookID = bookID;
        this.book = book;
        this.issuedDate = issuedDate;
        this.returnDate = returnDate;
        this.state = state;
    }

    public String getBookID() {
        return bookID;
    }

    public BookState getState() {
        return state;
    }

    public String getMember() {
        return member;
    }

    public String getBook() {
        return book;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
