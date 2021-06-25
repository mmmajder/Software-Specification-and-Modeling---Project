package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public PasswordField password;
    public TextField username;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void switchToUser(ActionEvent event) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/user.fxml"));
        final Parent root = (Parent) loader.load();
        final UserController controller = loader.getController();
        controller.initData();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("SIMS Library");
        stage.show();
        System.out.printf(String.valueOf(password.getText()));
        System.out.printf(String.valueOf(username.getText()));
    }

}
