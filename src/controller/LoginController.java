package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public TextField usernameTextField;
    public PasswordField passwordField;
    private Stage stage;
    private Scene scene;

    @FXML
    private void switchToUser(ActionEvent event) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/user.fxml"));
        final Parent root = (Parent) loader.load();
        final UserController controller = loader.getController();
        controller.initData();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setTitle("SIMS Library");
        stage.show();
    }

    @FXML
    private void alert(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("I have a great message for you!");
        alert.showAndWait();
    }
}