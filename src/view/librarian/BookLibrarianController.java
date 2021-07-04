package view.librarian;

import controller.AccountController;
import controller.EditionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
    public Label lblShelf;
    public Label lblGenre;
    public Text txtTags;
    public Text txtDescription;
    public Button btnRent;
    public Button btnEditSamples;
    public Button btnEditEdition;

    AccountController controller;
    LibrarianController librarianController;
    Library library;
    Account account;
    Edition edition;
    ILibraryRepo libraryRepo;
    EditionController editionControler;
    public BorderPane mainBorderPane;

    @FXML
    public void backToBooks() throws IOException {
        librarianController.switchToBooks();
    }

    @FXML
    public void switchToEditSamples(MouseEvent mouseEvent) throws IOException {
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/bookCRUD.fxml"));
        Parent bookScene = bookLoader.load();
        mainBorderPane.setCenter(bookScene);
        BookCRUDController bookCRUDController = bookLoader.getController();
        bookCRUDController.initData(edition, mainBorderPane, librarianController);
    }

    @FXML
    public void switchToEditEdition(MouseEvent mouseEvent) throws IOException {
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/createEdition.fxml"));
        Parent bookScene = bookLoader.load();
        mainBorderPane.setCenter(bookScene);
        CreateEditionController createEditionController = bookLoader.getController();
        //createEditionController.initData(edition, mainBorderPane, librarianController);
    }

    @FXML
    public void initData(Edition edition, BorderPane mainBorderPane, LibrarianController librarianController) {
        this.librarianController = librarianController;
        this.edition = edition;
        this.mainBorderPane = mainBorderPane;
        editionControler = new EditionController(library);
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
        lblGenre.setText("Genre " + edition.getGenres());
        txtTags.setText("Tags: " + edition.getTags());

        // TODO add needed getters
    }

}