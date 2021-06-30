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

    public SampleState getState() { return this.state; }
    public boolean isAvailable() { return this.state == SampleState.AVAILABLE; }
    public String getBookId() { return  this.bookId; }
    public void setState(SampleState state) { this.state = state; }
    public boolean getIsRestricted() { return this.isRestricted; }
    public void addIssueHistory(IssuedBook issuedBook) { this.issueHistory.add(issuedBook); }
    public Edition getEdition() { return edition; }
}
