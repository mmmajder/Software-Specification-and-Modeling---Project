package model;

import model.enums.Genre;

import java.time.LocalDate;
import java.util.ArrayList;

public class Edition {
    private String editionId;
    private ArrayList<String> tags;
    private String title;
    private String publisher;
    private int numberOfPages;
    private String description;
    private Genre genre;
    private LocalDate publishedDate;
    private String language;
    private String imageURL;
    private ArrayList<Book> books;
    private BookFormat format;
    private ArrayList<Contributor> contributors;

    public Edition(String editionId, String title, String publisher, Genre genre, String language, BookFormat format, ArrayList<Contributor> contributors) {
    }

    public Genre getGenre() {
        return genre;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getEditionId() {
        return editionId;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getLanguage() {
        return language;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public BookFormat getFormat() {
        return format;
    }

    public ArrayList<Contributor> getContributors() {
        return contributors;
    }
}
