package view.librarian.model;

import model.enums.BookState;

import java.time.LocalDate;

public class BookSampleTable {
    private LocalDate issueDate;
    private LocalDate returnedDate;
    private BookState state;
    private String member;

    public BookSampleTable(LocalDate issueDate, LocalDate returnedDate, BookState state, String member) {
        this.issueDate = issueDate;
        this.returnedDate = returnedDate;
        this.state = state;
        this.member = member;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public BookState getState() {
        return state;
    }

    public String getMember() {
        return member;
    }
}
