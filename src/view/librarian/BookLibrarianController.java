package view.librarian;

import controller.AccountController;
import controller.EditionController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;
import model.Edition;
import repository.ILibraryRepo;
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
    public Button btnRent;
    public Button btnEditSamples;
    public Button btnEditEdition;

    AccountController controller;
    LibrarianController librarianController;
    Library library;
    Account account;
    Edition edition;
    ILibraryRepo libraryRepo;
    EditionController editionController;
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
        bookCRUDController.initData(edition, mainBorderPane, librarianController, account);
    }

    @FXML
    public void switchToEditEdition(MouseEvent mouseEvent) throws IOException {
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/createEdition.fxml"));
        Parent bookScene = bookLoader.load();
        mainBorderPane.setCenter(bookScene);
        EditEditionController editionController = bookLoader.getController();
        editionController.fillData(edition);
    }

    @FXML
    public void initData(Edition edition, BorderPane mainBorderPane, LibrarianController librarianController, Account account) {
        this.librarianController = librarianController;
        this.library = new Library();
        this.edition = edition;
        this.mainBorderPane = mainBorderPane;
        this.account = account;
        editionController = new EditionController(library);
        lblTitle.setText(edition.getTitle());
        lblAuthor.setText(editionController.getAuthorName(edition));
        final Tooltip authorBiography = new Tooltip();
        authorBiography.setText(edition.getAuthorBiography());
        lblAuthor.setTooltip(authorBiography);

        txtDescription.setText(edition.getDescription());
        lblPublishedDate.setText("Published date: " + edition.getPublishedDate());
        lblLanguage.setText("Language: " + edition.getLanguage());
        lblPublisher.setText("Publisher: " + edition.getPublisher());
        lblNumberOfPages.setText("Name of pages: " + edition.getNumberOfPages());
        lblTranslation.setText("Translation: " + editionController.getTranslatorsStr(edition));
        lblIllustration.setText("Illustration: " + editionController.getIllustratorsStr(edition));
        lblGenre.setText("Genre " + editionController.getGenresConcatenated(edition));
        txtTags.setText("Tags: " + edition.getTags());

        btnRent.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/bookRent.fxml"));
                    Parent rentScene = bookLoader.load();
                    BookRentController bookRentController = bookLoader.getController();
                    bookRentController.initData(account, library);
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Book Rent");
                    primaryStage.getIcons().add(new Image("/fxml/logo.png"));
                    primaryStage.setScene(new Scene(rentScene, 400, 250));
                    primaryStage.setResizable(false);
                    primaryStage.show();
                    bookRentController = new BookRentController();
                    bookRentController.init();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
