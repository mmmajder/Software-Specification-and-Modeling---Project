package view.admin;

import controller.AccountController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.*;
import view.librarian.*;

import java.io.IOException;

public class AdminController {
    public Label lblBooks;
    public Label lblReservations;
    public Label lblMembers;
    public Label lblUsername;
    public Label lblRentedBooks;
    public Label lblSettings;

    public BorderPane borderPane;

    public Parent booksScene;
    public Parent reservationsScene;
    public Parent membersScene;
    public Parent rentedBooksScene;
    public Parent settingsScene;
    RentedBooksController rentedBooksController;
    MemberCRUDController membershipController;
    ReservationsLibrarianController reservationsLibrarianController;
    SearchBooksLibrarianController searchBooksLibrarianController;
    SettingsController settingsController;

    AccountController controller;
    LibrarianController librarianController;
    Library library;
    Account account;

    public void initData(Account account) throws IOException {
        this.library = new Library();
        this.controller = new AccountController(library);
        this.account = account;
        this.librarianController = new LibrarianController();
        lblUsername.setText(controller.getFullName(account.getPerson()));

        FXMLLoader booksLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/searchBooksLibrarian.fxml"));
        booksScene = booksLoader.load();
        searchBooksLibrarianController = booksLoader.getController();

        FXMLLoader reservationsLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/reservationsLibrarian.fxml"));
        reservationsScene = reservationsLoader.load();
        reservationsLibrarianController = reservationsLoader.getController();

        FXMLLoader membersLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/memberCRUD.fxml"));
        membersScene = membersLoader.load();
        membershipController = membersLoader.getController();

        FXMLLoader rentedBooksLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/rentedBooks.fxml"));
        rentedBooksScene = rentedBooksLoader.load();
        rentedBooksController = rentedBooksLoader.getController();

        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("../../fxml/admin/settings.fxml"));
        settingsScene = settingsLoader.load();
        settingsController = settingsLoader.getController();

        switchToBooks();
    }

    @FXML
    private void logOut(MouseEvent event) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fxml/login.fxml"));
        final Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void switchToRentedBooks() throws IOException {
        borderPane.setCenter(rentedBooksScene);
        rentedBooksController.initData();
        lblRentedBooks.setUnderline(true);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(false);
        lblSettings.setUnderline(false);
    }

    @FXML
    public void switchToBooks() {
        borderPane.setCenter(booksScene);
        searchBooksLibrarianController.initData(account, borderPane, librarianController);
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(true);
        lblSettings.setUnderline(false);
    }

    @FXML
    private void switchToMembers() throws IOException {
        borderPane.setCenter(membersScene);
        membershipController.initData();
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(true);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(false);
        lblSettings.setUnderline(false);
    }

    @FXML
    private void switchToReservations() throws IOException {
        borderPane.setCenter(reservationsScene);
        reservationsLibrarianController.initData(account);
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(true);
        lblBooks.setUnderline(false);
        lblSettings.setUnderline(false);
    }

    @FXML
    private void switchToSettings() throws IOException {
        borderPane.setCenter(settingsScene);
        settingsController.initData(account);
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(false);
        lblSettings.setUnderline(true);
    }
}
