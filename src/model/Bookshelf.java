package model;

import java.util.List;
import java.util.HashMap;

public class Bookshelf {

    private int shelfId;
    private HashMap<Integer, List<Book>> rows;

    public Bookshelf(int shelfId) {
        this.shelfId = shelfId;
        this.rows = new HashMap<>();
    }

    public int getShelfId() {
        return shelfId;
    }

    public HashMap<Integer, List<Book>> getRows() {
        return rows;
    }

    public List<Book> getBooks(int row) {
        return this.rows.get(row);
    }

}
