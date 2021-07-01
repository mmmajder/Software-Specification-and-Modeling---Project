package controller;

import model.*;
import utils.StringUtils;
import utils.exceptions.IdAlreadyExistsException;
import utils.exceptions.MissingValueException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EditionController {

    private Library library;

    public EditionController(Library library) {
        this.library = library;
    }

    //TODO kako je uradjen izbor Genre, Date, itd
    //da li se prebacuju ovde u stringu?
    public void create(String editionId, List<String> tags, String title, String publisher, int numberOfPages,
                       String description, Genre genre, LocalDate publishedDate, String language, List<Book> books,
                       BookFormat format, List<Contributor> contributors) throws MissingValueException, IdAlreadyExistsException {

//        validateInputValues(editionId, title, publisher, genre, language, format, contributors);
//        Edition e = EditionController.create(editionId, tags, title, publisher, numberOfPages,
//                description, genre, publishedDate, language, books, format, contributors);
//        library.addEdition(e);
    }

    private void validateInputValues(String editionId, String title, String publisher,
                                     Genre genre, String language, BookFormat format, ArrayList<Contributor> contributors) throws MissingValueException, IdAlreadyExistsException {
        if (StringUtils.isNullOrEmpty(editionId)) {
            throw new MissingValueException("editionId");
        }
        validateId(editionId);
        if (StringUtils.isNullOrEmpty(title)) {
            throw new MissingValueException("title");
        }
        if (StringUtils.isNullOrEmpty(publisher)) {
            throw new MissingValueException("publisher");
        }
        if (genre == null) {
            throw new MissingValueException("genre");
        }
        if (StringUtils.isNullOrEmpty(language)) {
            throw new MissingValueException("language");
        }
        if (format == null) {
            throw new MissingValueException("format");
        }
        if (contributors.size() == 0) {
            throw new MissingValueException("contributors");
        }
    }

    private void validateId(String editionId) throws IdAlreadyExistsException {
        if (EditionController.editionIdExists(library.getEditions(), editionId)) {
            throw new IdAlreadyExistsException();
        }
    }

//    static public Edition create(String editionId, List<String> tags, String title, String publisher, int numberOfPages,
//                                 String description, Genre genre, LocalDate publishedDate, String language, List<Book> books,
//                                 BookFormat format, List<Contributor> contributors) throws MissingValueException {
//        Edition e = new Edition(editionId, title, publisher, genre, language, format, contributors);
//        e.addOtherAttributes(tags, numberOfPages, description, publishedDate);
//
//        return e;
//    }

    public static boolean editionIdExists(List<Edition> editions, String editionId) {
        for (Edition e : editions) {
            if (e.getEditionId().equals(editionId)) {
                return true;
            }
        }

        return false;
    }

    public List<Edition> getRandom(int n){
        List<String> allEditionIds = library.getEditions().stream().map(edition -> edition.getEditionId()).collect(Collectors.toList());
        List<String> randomIds = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < n; i++){
            int randIndex = r.nextInt(allEditionIds.size());
            String randId = allEditionIds.get(randIndex);
            randomIds.add(randId);
        }

        List<Edition> randomEditions = library.getEditions().stream().filter(edition -> randomIds.contains(edition.getEditionId())).collect(Collectors.toList());

        return randomEditions;
    }
}
