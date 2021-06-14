package model;

import enums.Genre;

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
}
