package model;

import model.enums.ContributorType;
import utils.exceptions.IdAlreadyExistsException;
import utils.exceptions.MissingValueException;
import utils.exceptions.NoGenreOfSuchNameException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Edition(String editionId){
        this.editionId = editionId;
        this.contributorRoles = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    public Edition() {
        this.genres = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.books = new ArrayList<>();
        this.contributorRoles = new ArrayList<>();
    }

    public Edition(String editionId, String title, String publisher, int numberOfPages,
                   String description, Genre genre, LocalDate publishedDate, String language,
                   List<ContributorRole> contributorRoles){
        this();
        this.editionId = editionId;
        this.title = title;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.description = description;
        genres.add(genre);
        this.publishedDate = publishedDate;
        this.language = language;
        this.contributorRoles = contributorRoles;

    }

    public Edition(String editionId, String title, String publisher, int numberOfPages, String description, LocalDate publishedDate, String language, String image, BookFormat format) {
        this();
        this.editionId = editionId;
        this.title = title;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.description = description;
        this.publishedDate = publishedDate;
        this.language = language;
        this.image = image;
        this.format = format;
    }

    public Edition(String editionId, String title, String publisher, Genre genre, String language, BookFormat format, List<Contributor> contributors) {
        // fill constructor
    }


    public Contributor getAuthor() {
        for (ContributorRole cRole : contributorRoles) {
            if (cRole.getRole() == ContributorType.AUTHOR) {
                return cRole.getContributor();
            }
        }

        return null;
    }

    public List<Contributor> getAuthors(){ return getContributors(ContributorType.AUTHOR); }

    public List<Contributor> getTranslators(){ return getContributors(ContributorType.TRANSLATOR); }

    public List<Contributor> getIllustrators(){ return getContributors(ContributorType.ILLUSTRATOR); }

    public List<Contributor> getCritics(){ return getContributors(ContributorType.CRITIC); }

    public List<Contributor> getEditors(){ return getContributors(ContributorType.EDITOR); }

    public List<Contributor> getContributors(ContributorType type){
        return contributorRoles.stream()
                .filter(contributorRole -> contributorRole.getContributorType() == type)
                .map(contributorRole -> contributorRole.getContributor())
                .collect(Collectors.toList());
    }

    public String getAuthorBiography() {
        return this.getAuthor().getBiography();
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

    public List<Genre> getGenres() {
        return genres;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
}
