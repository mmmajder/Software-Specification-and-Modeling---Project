package view.librarian;

import controller.AccountController;
import controller.EditionController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import model.Account;
import model.Edition;
import model.ILibraryRepo;
import model.Library;

import java.io.IOException;

public class BookLibrarianController {
    public Label lblTitle;
    public Label lblAuthor;
    public Label lblPublishedDate;
    public Label lblLanguage;
    public Label lblPublisher;
    public Label lblNumberOfPages;
    public Label lblTranslation;
    public Label lblIllustration;
    public Label lblGenre;
    public Text txtTags;
    public Text txtDescription;

    AccountController controller;
    LibrarianController librarianController;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;
    EditionController editionControler;

    @FXML
    public void backToBooks() throws IOException {
        librarianController.switchToBooks();
    }

    @FXML
    public void initData(Edition edition, LibrarianController librarianController) {
        this.librarianController = librarianController;
        lblTitle.setText(edition.getTitle());
        lblAuthor.setText(editionControler.getAuthorName(edition));
        final Tooltip authorBiography = new Tooltip();
        authorBiography.setText(edition.getAuthorBiography());
        lblAuthor.setTooltip(authorBiography);

        txtDescription.setText(edition.getDescription());
        lblPublishedDate.setText("Published date: " + edition.getPublishedDate());
        lblLanguage.setText("Language: " + edition.getLanguage());
        lblPublisher.setText("Publisher: " + edition.getPublisher());
        lblNumberOfPages.setText("Name of pages: " + edition.getNumberOfPages());
        //lblTranslation.setText("Translation: " + edition.getTranslator());
        //lblIllustration.setText("Illustration: " + edition.getIllustrator());
//        lblGenre.setText("Genre " + edition.getGenre());
        txtTags.setText("Tags: " + edition.getTags());

        // TODO add needed getters
    }

}