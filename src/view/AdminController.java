package view;

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
import model.Account;
import model.Library;
import model.LibraryRepo;

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
    public Parent bookScene;
    public Parent reservationsScene;
    public Parent membersScene;
    public Parent rentedBooksScene;
    public Parent settingsScene;

    AccountController controller;
    Library library;
    Account account;
    LibraryRepo libraryRepo;

    public void initData(Account account) throws IOException {
        this.account = account;
        this.library = new Library();
        this.controller = new AccountController(library);
        libraryRepo = new LibraryRepo();
        libraryRepo.loadContributors(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadContributorRoles(library);
        libraryRepo.loadGenres(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadTags(library);
        libraryRepo.loadMaxIssueDays(library);
        libraryRepo.loadMaxIssuedBooks(library);
        lblUsername.setText(account.getFullName());

        FXMLLoader booksLoader = new FXMLLoader(getClass().getResource("../fxml/librarian/searchBooksLibrarian.fxml"));
        booksScene = booksLoader.load();
        //SearchBooksLibrarianController searchBooksLibrarianController = (SearchBooksLibrarianController) booksLoader.getController();

        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../fxml/librarian/bookLibrarian.fxml"));
        bookScene = bookLoader.load();
        //BookLibrarianController bookLibrarianController = (BookLibrarianController) bookLoader.getController();
        // TODO
        //bookLibrarianController.setSecondScene(new Scene(booksScene));
        //searchBooksLibrarianController.setSecondScene(new Scene(bookScene));

        FXMLLoader reservationsLoader = new FXMLLoader(getClass().getResource("../fxml/librarian/reservationsLibrarian.fxml"));
        reservationsScene = reservationsLoader.load();
        //HistoryController historyController = (HistoryController) historyLoader.getController();

        FXMLLoader membersLoader = new FXMLLoader(getClass().getResource("../fxml/librarian/memberCRUD.fxml"));
        membersScene = membersLoader.load();
        //MembershipController membershipController = (MembershipController) membershipLoader.getController();

        FXMLLoader rentedBooksLoader = new FXMLLoader(getClass().getResource("../fxml/librarian/rentedBooks.fxml"));
        rentedBooksScene = rentedBooksLoader.load();

        FXMLLoader settingsLoader = new FXMLLoader(getClass().getResource("../fxml/admin/settings.fxml"));
        settingsScene = settingsLoader.load();
        //NotificationsController notificationsController = (NotificationsController) notificationsLoader.getController();

        switchToBooks();
    }

    @FXML
    private void logOut(MouseEvent event) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/login.fxml"));
        final Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void switchToRentedBooks(MouseEvent event) {
        borderPane.setCenter(rentedBooksScene);
        lblRentedBooks.setUnderline(true);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(false);
        lblSettings.setUnderline(false);
    }

    @FXML
    private void switchToBooks(MouseEvent event) throws IOException {
        switchToBooks();
    }

    public void switchToBooks() {
        borderPane.setCenter(booksScene);
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(true);
        lblSettings.setUnderline(false);
    }

    @FXML
    private void switchToSettings(MouseEvent event) {
        borderPane.setCenter(settingsScene);
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(false);
        lblSettings.setUnderline(true);
    }

    @FXML
    private void switchToMembers(MouseEvent event) {
        borderPane.setCenter(membersScene);
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(true);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(false);
        lblSettings.setUnderline(false);
    }

    @FXML
    private void switchToReservations(MouseEvent event) {
        borderPane.setCenter(reservationsScene);
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(true);
        lblBooks.setUnderline(false);
        lblSettings.setUnderline(false);
    }

}
