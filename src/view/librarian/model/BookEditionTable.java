package view.librarian.model;

import model.enums.BookState;

public class BookEditionTable {
    private String bookId;
    private BookState state;
    private boolean isRestricted;
    private String position;

    public BookEditionTable(String bookId, BookState state, boolean isRestricted, String position) {
        this.bookId = bookId;
        this.state = state;
        this.isRestricted = isRestricted;
        this.position = position;
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

    public String getPosition() { return position; }
}
