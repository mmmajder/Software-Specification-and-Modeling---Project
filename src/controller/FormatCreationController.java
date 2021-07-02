package controller;

import model.BookFormat;
import model.Library;
import utils.exceptions.BookFormatAlreadyExistsException;
import utils.exceptions.MissingValueException;

public class FormatCreationController {

    private Library library;

    public FormatCreationController(Library library) {
        this.library = library;
    }

    public void create(double height, double width, double thickness) throws MissingValueException, BookFormatAlreadyExistsException {
        validateValues(height, width, thickness);
        checkIfAlreadyExists(height, width, thickness);
        //TODO value for new id
        int id = 0;
        library.addFormat(new BookFormat(id, height, width, thickness));
    }

    private void checkIfAlreadyExists(double height, double width, double thickness) throws BookFormatAlreadyExistsException {
        for (BookFormat format : library.getFormats()){
            if (format.getHeight() == height && format.getWidth() == width && format.getThickness() == thickness){
                throw new BookFormatAlreadyExistsException();
            }
        }
    }

    private void validateValues(Double height, Double width, Double thickness) throws MissingValueException {
        validateValue(height, "height");
        validateValue(height, "width");
        validateValue(height, "thickness");
    }

    private void validateValue(Double value, String valueName) throws MissingValueException {
        if (value == null || value <= 0){
            throw new MissingValueException(valueName);
        }
    }

}
