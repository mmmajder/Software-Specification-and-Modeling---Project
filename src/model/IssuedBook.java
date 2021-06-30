package model;

import java.time.LocalDate;

public class IssuedBook {
    private LocalDate issueDate;
    private LocalDate returnDate;
    private boolean prolongedIssue;
    private  Book book;
    private Member member;

    public IssuedBook(Member member, Book book){
        this.issueDate = LocalDate.now();
        this.returnDate = null;
        this.prolongedIssue = false;
        this.book = book;
        this.member = member;
    }

    public IssuedBook(LocalDate issueDate, Member member, Book book){
        this.issueDate = issueDate;
        this.returnDate = null;
        this.prolongedIssue = false;
        this.book = book;
        this.member = member;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isProlongedIssue() {
        return prolongedIssue;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }
}
