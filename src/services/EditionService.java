package services;

import model.Book;
import model.BookFormat;
import model.Contributor;
import model.Edition;
import model.enums.Genre;
import utils.exceptions.MissingValueException;

import java.time.LocalDate;
import java.util.ArrayList;

public class EditionService {

    static public Edition create(String editionId, ArrayList<String> tags, String title, String publisher, int numberOfPages,
                                     String description, Genre genre, LocalDate publishedDate, String language, ArrayList<Book> books,
                                     BookFormat format, ArrayList<Contributor> contributors) throws MissingValueException {
        Edition e = new Edition(editionId, title, publisher, genre, language, format, contributors);
        e.addOtherAttributes(tags, numberOfPages, description, publishedDate);

        return e;
    }

    public static boolean doesEditionIdExist(ArrayList<Edition> editions, String editionId){
        for (Edition e : editions){
            if (e.getEditionId().equals(editionId)){
                return true;
            }
        }

        return false;
    }
}
