package view.librarian;

import javafx.scene.control.ComboBox;

public class CreateEditionController {

    public ComboBox<String> writerCB;
    public ComboBox languageCB;
    public ComboBox tagsCB;
    public ComboBox genreCB;
    public ComboBox publisherCB;

    LibrarianController librarianController;

    public void initData(LibrarianController librarianController) {
        this.librarianController = librarianController;
    }
}
