package view.librarian;

import controller.EditionController;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import model.Contributor;
import model.Edition;
import model.Genre;
import model.Library;

public class EditEditionController {

    public ComboBox<Contributor> writerCB;
    public ComboBox<Genre> genresCB;
    public TextField publisher;
    public TextField title;
    public DatePicker publishedDate;
    public TextField language;
    public TextField numberOfPages;
    public TextArea description;
    public EditionController editionController;
    public Library library;

    LibrarianController librarianController;

    public void fillData(Edition edition) {
        initData();
        language.setText(edition.getLanguage());
        numberOfPages.setText(String.valueOf(edition.getNumberOfPages()));
        title.setText(edition.getTitle());
        publishedDate.setValue(edition.getPublishedDate());
        publisher.setText(edition.getPublisher());
        description.setText(edition.getDescription());
        writerCB.getSelectionModel().select(edition.getAuthor());
        genresCB.getSelectionModel().select(edition.getGenres().get(0));
    }

    public void initData() {
        this.library = new Library();
        this.editionController = new EditionController(library);
        this.librarianController = new LibrarianController();

        description.setWrapText(true);

        writerCB.setConverter(new StringConverter<Contributor>() {
            @Override
            public String toString(Contributor object) {
                return object.getFullName();
            }

            @Override
            public Contributor fromString(String fullName) {
                return library.fromFullName(fullName);
            }
        });
        writerCB.setItems(FXCollections.observableArrayList(library.getContributors()));

        genresCB.setConverter(new StringConverter<Genre>() {
            @Override
            public String toString(Genre object) {
                return object.getName();
            }

            @Override
            public Genre fromString(String name) {
                return library.fromName(name);
            }
        });
        genresCB.setItems(FXCollections.observableArrayList(library.getGenres()));
    }
}
