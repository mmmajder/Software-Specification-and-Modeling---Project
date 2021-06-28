package model;

import model.enums.Genre;
import utils.StringUtils;
import utils.exceptions.MissingValueException;

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
    //private Image image;
    private ArrayList<Book> books;
    private BookFormat format;
    private ArrayList<Contributor> contributors;



    public Edition(String editionId, String title, String publisher, Genre genre, String language, BookFormat format, ArrayList<Contributor> contributors){
        this.editionId = editionId;
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
        this.language = language;
        this.format = format;
        this.contributors = contributors;
        this.books = new ArrayList<Book>();
    }

    // other -> can be null
    public void addOtherAttributes(ArrayList<String> tags, int numberOfPages, String description,
                                   LocalDate publishedDate){
        this.tags = tags;
        this.numberOfPages = numberOfPages;
        this.description = description;
        this.publishedDate = publishedDate;
    }


}
