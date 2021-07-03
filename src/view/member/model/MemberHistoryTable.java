package view.member.model;

import model.enums.IssuedBookState;

import java.time.LocalDate;

public class MemberHistoryTable {
    private String bookTitle;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private LocalDate returnedDate;
    private IssuedBookState state;

    public MemberHistoryTable(String bookTitle, LocalDate issueDate, LocalDate returnDate, LocalDate returnedDate, IssuedBookState state) {
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.returnedDate = returnedDate;
        this.state = state;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public IssuedBookState getState() {
        return state;
    }
}
