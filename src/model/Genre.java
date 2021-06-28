package model;

import java.util.ArrayList;
import java.util.List;

public class Genre {

    private int genreId;
    private String name;
    private List<Edition> editions;

    public Genre() {
        this.editions = new ArrayList<>();
    }

    public Genre(int genreId, String name) {
        this.genreId = genreId;
        this.name = name;
        this.editions = new ArrayList<>();
    }

    public void addEdition(Edition edition) {
        this.editions.add(edition);
    }
}
