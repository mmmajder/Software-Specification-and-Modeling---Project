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

    public String getName() {
        return name;
    }

    public int getGenreId() { return genreId; }

    public void setGenreId(int genreId) { this.genreId = genreId; }

    public void setName(String name) { this.name = name; }

    public List<Edition> getEditions() { return editions; }

    public void setEditions(List<Edition> editions) { this.editions = editions; }

    public void addEdition(Edition edition) {
        this.editions.add(edition);
    }


}
