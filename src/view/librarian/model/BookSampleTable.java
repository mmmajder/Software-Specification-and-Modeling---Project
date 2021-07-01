package view.librarian.model;

import model.enums.SampleState;

import java.time.LocalDate;

public class BookSampleTable {
    private LocalDate issueDate;
    private LocalDate returnedDate;
    private SampleState state;
    private String member;
}
