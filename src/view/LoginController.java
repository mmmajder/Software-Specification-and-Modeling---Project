package view;

import controller.AccountController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Account;
import repository.ILibraryRepo;
import model.Library;
import repository.LibraryRepo;
import utils.exceptions.InvalidAccountPassword;
import utils.exceptions.NoAccountWithThatUsername;
import view.admin.AdminController;
import view.librarian.LibrarianController;
import view.member.MemberController;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    public TextField usernameTextField;
    public PasswordField passwordField;
    public Label lblError;
    AccountController accountController;
    Library library;
    ILibraryRepo libraryRepo;

    private String getFileName(Account account) {
        switch (account.getType()) {
            case ADMIN:
                return "../fxml/admin/admin.fxml";
            case MEMBER:
                return "../fxml/member/member.fxml";
            case LIBRARIAN:
                return "../fxml/librarian/librarian.fxml";
            default:
                return null;
        }
    }

    private void setController(Account account, FXMLLoader loader) throws IOException {
        switch (account.getType()) {
            case ADMIN:
                final AdminController adminController = loader.getController();
                adminController.initData(account);
                break;
            case MEMBER:
                final MemberController memberController = loader.getController();
                memberController.initData(account);
                break;
            case LIBRARIAN:
                final LibrarianController librarianController = loader.getController();
                librarianController.initData(account);
                break;
        }
    }

    @FXML
    private void switchToUser(ActionEvent event) throws IOException {
        try {
            this.library = new Library();
            this.accountController = new AccountController(library);
            libraryRepo = new LibraryRepo();
            libraryRepo.loadAccounts(library);
            libraryRepo.loadPersons(library);
            Account account = accountController.getAccount(usernameTextField.getText(), passwordField.getText());
            final FXMLLoader loader = new FXMLLoader(getClass().getResource(Objects.requireNonNull(getFileName(account))));
            final Parent root = loader.load();
            setController(account, loader);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMinWidth(950);
            stage.setMinHeight(650);
            stage.setTitle("SIMS Library");
            stage.show();
        } catch (NoAccountWithThatUsername | InvalidAccountPassword noAccountWithThatUsername) {
            lblError.setVisible(true);
        }
    }
}