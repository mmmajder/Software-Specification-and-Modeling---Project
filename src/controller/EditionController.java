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

    //TODO kako je uradjen izbor Genre, Date, itd
    //da li se prebacuju ovde u stringu?
    static public void create(String editionId, ArrayList<String> tags, String title, String publisher, int numberOfPages,
                              String description, Genre genre, LocalDate publishedDate, String language, ArrayList<Book> books,
                              BookFormat format, ArrayList<Contributor> contributors) throws MissingValueException, IdAlreadyExistsException {

        if (StringUtils.isNullOrEmpty(editionId)) { throw new MissingValueException("editionId"); }

//        Library l;
//        if (l.doesEditionIdExist(editionId)){ throw new IdAlreadyExistsException(); }

        if (StringUtils.isNullOrEmpty(title)) { throw new MissingValueException("title"); }
        if (StringUtils.isNullOrEmpty(publisher)) { throw new MissingValueException("publisher"); }
        if (genre == null) { throw new MissingValueException("genre"); }
        if (StringUtils.isNullOrEmpty(language)) { throw new MissingValueException("language"); }
        if (format == null) { throw new MissingValueException("format"); }
        if (contributors.size() == 0) { throw new MissingValueException("contributors"); }

        Edition e = EditionService.create(editionId, tags, title, publisher, numberOfPages,
                description, genre, publishedDate, language, books, format, contributors);

    }
}
