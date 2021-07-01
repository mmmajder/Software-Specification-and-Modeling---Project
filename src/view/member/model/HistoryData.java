package view.member.model;

import model.enums.SampleState;

import java.time.LocalDate;

public class HistoryData {
    private String bookTitle;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private SampleState state;

    public HistoryData(String bookTitle, LocalDate issueDate, LocalDate returnDate, SampleState state) {
        this.bookTitle = bookTitle;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.state = state;
    }
}
