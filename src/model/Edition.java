package model;

import model.enums.ContributorType;

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
    private List<Genre> genres;
    private LocalDate publishedDate;
    private String language;
    private String image;
    private List<Book> books;
    private BookFormat format;
    private List<ContributorRole> contributorRoles;

    public Edition() {
        this.genres = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.books = new ArrayList<>();
        this.contributorRoles = new ArrayList<>();
    }

    public Edition(String editionId, String title, String publisher, int numberOfPages, String description, LocalDate publishedDate, String language, String image, BookFormat format) {
        this.editionId = editionId;
        this.title = title;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.description = description;
        this.publishedDate = publishedDate;
        this.language = language;
        this.image = image;
        this.format = format;
        this.genres = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.books = new ArrayList<>();
        this.contributorRoles = new ArrayList<>();
    }

    public Edition(String editionId, String title, String publisher, Genre genre, String language, BookFormat format, List<Contributor> contributors) {
        // fill constructor
    }


    public Contributor getAuthor(){
        for (ContributorRole cRole : contributorRoles){
            if (cRole.getContributorType() == ContributorType.AUTHOR){
                return cRole.getContributor();
            }
        }
        
        return null;
    }

    public String getImage() {
        return image;
    }

    public String getEditionId() {
        return editionId;
    }

    public List<String> getTags() {
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

    public List<Book> getBooks() {
        return books;
    }

    public BookFormat getFormat() {
        return format;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
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
