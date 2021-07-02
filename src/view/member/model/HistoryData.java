package view.member.model;

import model.enums.BookState;

import java.time.LocalDate;

public class HistoryData {
    private String bookTitle;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private BookState state;

    public HistoryData(String bookTitle, LocalDate issueDate, LocalDate returnDate, BookState state) {
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.state = state;
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

    public BookState getState() {
        return state;
    }
}
