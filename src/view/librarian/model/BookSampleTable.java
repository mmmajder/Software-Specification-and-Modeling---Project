package view.librarian.model;

import model.enums.SampleState;

import java.time.LocalDate;

public class BookSampleTable {
    private LocalDate issueDate;
    private LocalDate returnedDate;
    private SampleState state;
    private String member;

    public BookSampleTable(LocalDate issueDate, LocalDate returnedDate, SampleState state, String member) {
        this.issueDate = issueDate;
        this.returnedDate = returnedDate;
        this.state = state;
        this.member = member;
    }
}
