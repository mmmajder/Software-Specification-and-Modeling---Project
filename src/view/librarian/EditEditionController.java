package view.librarian;

import javafx.scene.control.*;
import model.Edition;

import java.time.format.DateTimeFormatter;

public class EditEditionController {

    public ComboBox<String> writerCB;
    public ComboBox<String> publisherCB;
    public TextField title;
    public DatePicker publishedDate;
    public TextField language;
    public Spinner<Integer> numberOfPages;
    public TextArea description;

    LibrarianController librarianController;

    public EditEditionController(Edition edition) {
        language.setText(edition.getLanguage());
        numberOfPages.getValueFactory().setValue(edition.getNumberOfPages());
        title.setText(edition.getTitle());
        publishedDate.setValue(edition.getPublishedDate());
        description.setText(edition.getDescription());
    }

    public void initData(LibrarianController librarianController) {
        this.librarianController = librarianController;
    }
}
