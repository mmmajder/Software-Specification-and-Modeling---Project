package model;

import model.enums.Genre;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Edition {

    private String editionId;
    private List<String> tags;
    private String title;
    private String publisher;
    private int numberOfPages;
    private String description;
    private Genre genre;
    private LocalDate publishedDate;
    private String language;
    private Image image;
    private List<Book> books;
    private BookFormat format;
    private List<ContributorRole> contributorRoles;

    public Edition() {
        this.tags = new ArrayList<>();
        this.books = new ArrayList<>();
        this.contributorRoles = new ArrayList<>();
    }

    public Edition(String editionId, String title, String publisher, int numberOfPages, String description, Genre genre, LocalDate publishedDate, String language, Image image, BookFormat format) {
        this.editionId = editionId;
        this.title = title;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.description = description;
        this.genre = genre;
        this.publishedDate = publishedDate;
        this.language = language;
        this.image = image;
        this.format = format;
        this.tags = new ArrayList<>();
        this.books = new ArrayList<>();
        this.contributorRoles = new ArrayList<>();
    }

    public String getEditionId() {
        return editionId;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addContributorRole(ContributorRole role) {
        this.contributorRoles.add(role);
    }
}
