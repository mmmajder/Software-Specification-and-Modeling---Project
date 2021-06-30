package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Edition;

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
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setSecondScene(Scene scene) {
        this.scene = scene;
    }

    public void backToBooks(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
    }

    @FXML
    private void alert(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Alert");
        a.setContentText("Use");
    }

    @FXML
    public void initData(Edition edition) {
        lblTitle.setText(edition.getTitle());
        //lblAuthor.setText(edition.getAuthor());
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

    @FXML
    private void openWritter(MouseEvent event) {
//        FXMLLoader writter = new FXMLLoader(getClass().getResource("../fxml/writter.fxml"));
//        writtetScene = writter.load();

    }
}