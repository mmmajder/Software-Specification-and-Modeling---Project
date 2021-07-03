package view.librarian;

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
import model.ILibraryRepo;
import model.Library;
import model.LibraryRepo;

import java.io.IOException;

public class LibrarianController {
    public Label lblBooks;
    public Label lblReservations;
    public Label lblMembers;
    public Label lblUsername;
    public Label lblRentedBooks;

    public BorderPane borderPane;

    public Parent booksScene;
    public Parent reservationsScene;
    public Parent membersScene;
    public Parent rentedBooksScene;
    RentedBooksController rentedBooksController;
    MemberCRUDController membershipController;
    ReservationsLibrarianController reservationsLibrarianController;
    SearchBooksLibrarianController searchBooksLibrarianController;

    AccountController controller;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;

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
    }

    @FXML
    public void switchToBooks() {
        borderPane.setCenter(booksScene);
        searchBooksLibrarianController.initData(account, borderPane, this);
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(true);
    }

    @FXML
    private void switchToMembers() throws IOException {
        borderPane.setCenter(membersScene);
        membershipController.initData();
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(true);
        lblReservations.setUnderline(false);
        lblBooks.setUnderline(false);
    }

    @FXML
    private void switchToReservations() throws IOException {
        borderPane.setCenter(reservationsScene);
        reservationsLibrarianController.initData();
        lblRentedBooks.setUnderline(false);
        lblMembers.setUnderline(false);
        lblReservations.setUnderline(true);
        lblBooks.setUnderline(false);
    }

}
