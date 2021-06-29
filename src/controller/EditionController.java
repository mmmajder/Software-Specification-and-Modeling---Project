package controller;

import model.*;
import model.enums.Genre;
import services.EditionService;
import utils.StringUtils;
import utils.exceptions.IdAlreadyExistsException;
import utils.exceptions.MissingValueException;

import java.time.LocalDate;
import java.util.ArrayList;

public class EditionController {

    private ArrayList<Edition> editions;

    public EditionController(ArrayList<Edition> editions){
        this.editions = editions;
    }

    //TODO kako je uradjen izbor Genre, Date, itd
    //da li se prebacuju ovde u stringu?
    public void create(String editionId, ArrayList<String> tags, String title, String publisher, int numberOfPages,
                              String description, Genre genre, LocalDate publishedDate, String language, ArrayList<Book> books,
                              BookFormat format, ArrayList<Contributor> contributors) throws MissingValueException, IdAlreadyExistsException {
        validateInputValues(editionId, title, publisher, genre, language, format, contributors);
        Edition e = EditionService.create(editionId, tags, title, publisher, numberOfPages,
                description, genre, publishedDate, language, books, format, contributors);
        editions.add(e);
    }

    private void validateInputValues(String editionId, String title, String publisher,
                                     Genre genre, String language, BookFormat format, ArrayList<Contributor> contributors) throws MissingValueException, IdAlreadyExistsException {
        if (StringUtils.isNullOrEmpty(editionId)) { throw new MissingValueException("editionId"); }
        validateId(editionId);
        if (StringUtils.isNullOrEmpty(title)) { throw new MissingValueException("title"); }
        if (StringUtils.isNullOrEmpty(publisher)) { throw new MissingValueException("publisher"); }
        if (genre == null) { throw new MissingValueException("genre"); }
        if (StringUtils.isNullOrEmpty(language)) { throw new MissingValueException("language"); }
        if (format == null) { throw new MissingValueException("format"); }
        if (contributors.size() == 0) { throw new MissingValueException("contributors"); }
    }

    private void validateId(String editionId) throws IdAlreadyExistsException {
        if (EditionService.doesEditionIdExist(editions, editionId)){ throw new IdAlreadyExistsException(); }
    }
}
