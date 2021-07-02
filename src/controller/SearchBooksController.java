package controller;

import model.Library;
import model.Sorter;

public class SearchBooksController {

    private Library library;

    public SearchBooksController(Library library) {
        this.library = library;
    }

    public void sortByTitleAsc() {
        this.library.getEditions().sort(Sorter.EditionTitleAscComparator);
        this.library.notifyObservers();
    }

    public void sortByPublishedDateAsc() {
        this.library.getEditions().sort(Sorter.EditionPublishedDateAscComparator);
        this.library.notifyObservers();
    }
}
