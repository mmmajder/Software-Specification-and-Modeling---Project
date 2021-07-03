package model;

import java.util.List;

public class BookSection {

    private String sectionId;
    private List<Bookshelf> shelves;

    public BookSection(String sectionId, List<Bookshelf> shelves) {
        this.sectionId = sectionId;
        this.shelves = shelves;
    }

    public String getSectionId() {
        return sectionId;
    }

    public List<Bookshelf> getShelves() {
        return shelves;
    }
}
