package view.librarian.model;

import model.enums.BookState;

public class BookEditionTable {
    private String bookId;
    private BookState state;
    private boolean isRestricted;

    public BookEditionTable(String bookId, BookState state, boolean isRestricted) {
        this.bookId = bookId;
        this.state = state;
        this.isRestricted = isRestricted;
    }

    public String getBookId() {
        return bookId;
    }

    public BookState getState() {
        return state;
    }

    public boolean isRestricted() {
        return isRestricted;
    }
}
