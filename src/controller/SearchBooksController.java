package controller;

import model.Genre;
import model.Library;
import model.Sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class SearchBooksController {

    private Library library;

    public SearchBooksController(Library library) {
        this.library = library;
    }

    public List<String> getGenreNamesSorted() {
        List<String> genresNames = new ArrayList<>();
        for (Genre genre : library.getGenres()) {
            genresNames.add(genre.getName());
        }
        Collections.sort(genresNames);
        return new ArrayList<>(new HashSet<>(genresNames));
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
