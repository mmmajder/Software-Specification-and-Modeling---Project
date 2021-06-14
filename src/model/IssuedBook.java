package model;

import java.time.LocalDate;

public class IssuedBook {
    LocalDate issueDate;
    LocalDate returnDate;
    boolean prolongedIssue;
    Book book;

    public IssuedBook(LocalDate issueDate, Book book){
        this.issueDate = issueDate;
        this.returnDate = null;
        this.prolongedIssue = false;
        this.book = book;
    }
}
