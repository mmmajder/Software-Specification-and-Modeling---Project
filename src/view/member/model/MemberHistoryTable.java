package view.member.model;

import java.time.LocalDate;

public class MemberHistoryTable {
    private String id;
    private String bookTitle;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private LocalDate returnedDate;
    private Boolean prolonged;
    private String state;

    public MemberHistoryTable(String id, String bookTitle, LocalDate issueDate, LocalDate returnDate, LocalDate returnedDate, Boolean prolonged, String state) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.returnedDate = returnedDate;
        this.prolonged = prolonged;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public Boolean getProlonged() {
        return prolonged;
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
