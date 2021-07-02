package view.librarian;

import controller.AccountController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

    @FXML
    public void backToBooks() throws IOException {
        librarianController.switchToBooks();
    }

    @FXML
    public void initData(Edition edition, LibrarianController librarianController) {
        this.librarianController = librarianController;
        lblTitle.setText(edition.getTitle());
        //lblAuthor.setText(edition.getAuthor());
        final Tooltip authorBiography = new Tooltip();
        //authorBiography.setText(edition.getAuthor().getBiography());
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