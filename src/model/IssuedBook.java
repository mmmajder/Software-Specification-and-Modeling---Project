package model;

import java.time.LocalDate;

public class IssuedBook {
    private LocalDate issueDate;
    private LocalDate returnDate;
    private boolean prolongedIssue;
    private  Book book;
    private Member member;

    public IssuedBook(LocalDate issueDate, Book book){
        this.issueDate = issueDate;
        this.returnDate = null;
        this.prolongedIssue = false;
        this.book = book;
    }
}
