package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void switchToUser(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/user.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("SIMS Library");
        stage.show();
    }

}
