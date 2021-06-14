package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Edition {
    String editionId;
    ArrayList<String> tags;
    String title;
    String publisher;
    int numberOfPages;
    String description;
    Genre genre;
    LocalDate publishedDate;
    String language;
    //Image image;
    ArrayList<Book> books;
    BookFormat format;
}
