package controller;

import model.Edition;
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

    public List<Edition> sortByTitleAsc(List<Edition> currentEditions) {

        currentEditions.sort(Sorter.EditionTitleAscComparator);
        return currentEditions;
    }

    public List<Edition> sortByPublishedDateAsc(List<Edition> currentEditions) {

        currentEditions.sort(Sorter.EditionPublishedDateAscComparator);
        return currentEditions;
    }

    public List<Edition> filterByGenre(List<Edition> currentEditions, String genreName) {

        List<Edition> editions = new ArrayList<>();
        for (Edition edition : currentEditions) {

            for (Genre genre : edition.getGenres()) {

                if (genre.getName().equals(genreName)) {
                    editions.add(edition);
                    break;
                }
            }
        }

        return editions;
    }

    public List<Edition> filterByTitle(List<Edition> currentEditions, String title) {

        List<Edition> editions = new ArrayList<>();
        for (Edition edition : currentEditions) {

            if (edition.getTitle().contains(title)) {

                editions.add(edition);
            }
        }

        return editions;
    }
}
