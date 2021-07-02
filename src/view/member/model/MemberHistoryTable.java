package view.member.model;

import java.time.LocalDate;

public class MemberHistoryTable {
    private String bookTitle;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private String state;

    public MemberHistoryTable(String bookTitle, LocalDate issueDate, LocalDate returnDate, String state) {
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

    public String getState() {
        return state;
    }
}
