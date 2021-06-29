package view;

import controller.AccountController;
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
import model.Account;
import model.Library;
import model.LibraryRepo;
import utils.exceptions.InvalidAccountPassword;
import utils.exceptions.NoAccountWithThatUsername;

import java.io.IOException;

public class LoginController {
    public TextField usernameTextField;
    public PasswordField passwordField;
    AccountController controller;
    Library library;

    private String getFileName(Account account) {
        switch (account.getType()) {
            case ADMIN:
                return "../fxml/user.fxml";
            case MEMBER:
                return "../fxml/user.fxml";
            case LIBRARIAN:
                return "../fxml/librarian.fxml";
            default:
                return null;
        }
    }

    @FXML
    private void switchToUser(ActionEvent event) throws IOException {
        try {
            this.library = new Library();
            this.controller = new AccountController(library);
            LibraryRepo libraryRepo = new LibraryRepo();
            libraryRepo.loadAccounts(library);
            System.out.println(usernameTextField.getText().toString());
            if (controller.usernameExists(usernameTextField.getText().toString())) {
                Account account = library.getAccount(usernameTextField.getText().toString());
                if (controller.passwordValid(account, passwordField.getText().toString())) {
                    final FXMLLoader loader = new FXMLLoader(getClass().getResource(getFileName(account)));
                    final Parent root = (Parent) loader.load();
                    final UserController controller = loader.getController();
                    controller.initData();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.setMinWidth(900);
                    stage.setMinHeight(600);
                    stage.setTitle("SIMS Library");
                    stage.show();
                }
            }
        } catch (NoAccountWithThatUsername noAccountWithThatUsername) {
            noAccountWithThatUsername.printStackTrace();
        } catch (InvalidAccountPassword invalidAccountPassword) {
            invalidAccountPassword.printStackTrace();
        }
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