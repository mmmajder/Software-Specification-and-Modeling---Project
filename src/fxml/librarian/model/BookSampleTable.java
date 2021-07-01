package fxml.librarian.model;

import model.Member;
import model.enums.SampleState;

import java.time.LocalDate;

public class BookSampleTable {
    private LocalDate issueDate;
    private LocalDate returnedDate;
    private SampleState state;
    private String member;
}
