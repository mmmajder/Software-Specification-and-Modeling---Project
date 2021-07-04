package view.member;

import controller.AccountController;
import controller.EditionController;
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

public class BookMemberController {
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
    MemberController memberController;
    EditionController editionController;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    @FXML
    public void backToBooks() throws IOException {
        memberController.switchToBooks();
    }

    @FXML
    private void alert(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Alert");
        a.setContentText("Use");
    }

    @FXML
    public void initData(Edition edition, MemberController memberController, Account account) {
        this.memberController = memberController;
        this.account = account;
        editionController = new EditionController(library);
        lblTitle.setText(edition.getTitle());
        lblAuthor.setText(editionController.getAuthorName(edition));
        final Tooltip authorBiography = new Tooltip();
        authorBiography.setText(edition.getAuthor().getBiography());
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