package model;

import model.enums.SampleState;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String bookId;
    private SampleState state;
    private List<IssuedBook> issueHistory;
    private Edition edition;
    private boolean isRestricted;

    public Book() {
        this.issueHistory = new ArrayList<>();
    }

    public Book(String bookId, SampleState state, Edition edition, boolean isRestricted) {
        this.bookId = bookId;
        this.state = state;
        this.edition = edition;
        this.isRestricted = isRestricted;
        this.issueHistory = new ArrayList<>();
    }
}
