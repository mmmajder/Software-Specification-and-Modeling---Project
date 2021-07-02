package model;

import java.time.LocalDate;

public class IssuedBook {
    private LocalDate issueDate;
    private LocalDate returnDate;
    private boolean prolongedIssue;
    private  Book book;
    private Librarian librarian;
    private Member member;

    public IssuedBook(LocalDate issueDate, LocalDate returnDate, boolean prolongedIssue, Book book, Librarian librarian,
                      Member member) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.prolongedIssue = prolongedIssue;
        this.book = book;
        this.librarian = librarian;
        this.member = member;
    }

    public IssuedBook(LocalDate issueDate, boolean prolongedIssue, Book book, Librarian librarian, Member member) {
        this.issueDate = issueDate;
        this.prolongedIssue = prolongedIssue;
        this.book = book;
        this.librarian = librarian;
        this.member = member;
    }

    public IssuedBook(Member member, Book book, Librarian librarian){
        this.issueDate = LocalDate.now();
        this.returnDate = null;
        this.prolongedIssue = false;
        this.book = book;
        this.member = member;
        this.librarian = librarian;
    }

    public IssuedBook(LocalDate issueDate, Member member, Book book){
        this.issueDate = issueDate;
        this.returnDate = null;
        this.prolongedIssue = false;
        this.book = book;
        this.member = member;
    }

    public Librarian getLibrarian() {
        return librarian;
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
