package main;

import controller.AccountController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.ILibraryRepo;
import model.Library;
import model.LibraryRepo;

import java.util.Objects;

public class Login extends Application {

    ILibraryRepo libraryRepo;
    Library library;
    AccountController controller;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.library = new Library();
        this.controller = new AccountController(library);
        this.libraryRepo = new LibraryRepo();

        libraryRepo.loadAccounts(library);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../fxml/login.fxml")));
        primaryStage.setTitle("Login");
        primaryStage.getIcons().add(new Image("/fxml/logo.png"));
        primaryStage.setScene(new Scene(root, 950, 650));
        primaryStage.setMinWidth(950);
        primaryStage.setMinHeight(650);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
