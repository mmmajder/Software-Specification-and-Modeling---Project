package model;

import model.enums.BookState;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String bookId;
    private BookState state;
    private List<IssuedBook> issueHistory;
    private Edition edition;
    private boolean isRestricted;

    public Book() {
        this.issueHistory = new ArrayList<>();
    }

    public Book(String bookId, BookState state, Edition edition, boolean isRestricted) {
        this.bookId = bookId;
        this.state = state;
        this.edition = edition;
        this.isRestricted = isRestricted;
        this.issueHistory = new ArrayList<>();
    }

    public BookState getState() { return this.state; }

    public boolean isAvailable() { return this.state == BookState.AVAILABLE; }

    public String getBookId() { return  this.bookId; }

    public void setState(BookState state) { this.state = state; }

    public boolean getIsRestricted() { return this.isRestricted; }

    public void addIssueHistory(IssuedBook issuedBook) { this.issueHistory.add(issuedBook); }

    public Edition getEdition() { return edition; }

    public List<IssuedBook> getIssueHistory() { return issueHistory; }

    public void makeAvailable() { this.state = BookState.AVAILABLE; }

    public void makeTaken() {this.state = BookState.TAKEN; }
}
