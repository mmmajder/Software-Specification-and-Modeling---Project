package view.librarian.model;

import model.enums.SampleState;

public class BookEditionTable {
    private String bookId;
    private SampleState state;
    private boolean isRestricted;

    public BookEditionTable(String bookId, SampleState state, boolean isRestricted) {
        this.bookId = bookId;
        this.state = state;
        this.isRestricted = isRestricted;
    }

    public String getBookId() {
        return bookId;
    }

    public SampleState getState() {
        return state;
    }

    public boolean isRestricted() {
        return isRestricted;
    }
}
