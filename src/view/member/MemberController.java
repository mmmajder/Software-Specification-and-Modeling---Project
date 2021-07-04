package view.member;

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
import repository.ILibraryRepo;
import model.Library;
import repository.LibraryRepo;

import java.io.IOException;

public class MemberController {
    public Label lblNotifications;
    public Label lblBooks;
    public Label lblHistory;
    public Label lblMembership;
    public Label lblUsername;

    public BorderPane borderPane;

    public Parent booksScene;
    public Parent historyScene;
    public Parent membershipScene;
    public Parent notificationsScene;

    MembershipController membershipController;
    NotificationsController notificationsController;
    SearchBooksMemberController searchBooksMemberController;
    IssuedBooksHistoryController issuedBooksHistoryController;

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

        FXMLLoader booksLoader = new FXMLLoader(getClass().getResource("../../fxml/member/searchBooksMember.fxml"));
        booksScene = booksLoader.load();
        searchBooksMemberController = booksLoader.getController();

        FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("../../fxml/member/issuedBooksHistory.fxml"));
        historyScene = historyLoader.load();
        issuedBooksHistoryController = historyLoader.getController();

        FXMLLoader membershipLoader = new FXMLLoader(getClass().getResource("../../fxml/member/membership.fxml"));
        membershipScene = membershipLoader.load();
        membershipController = membershipLoader.getController();

        FXMLLoader notificationsLoader = new FXMLLoader(getClass().getResource("../../fxml/member/notifications.fxml"));
        notificationsScene = notificationsLoader.load();
        notificationsController = notificationsLoader.getController();

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
    private void switchToNotifications() throws IOException {
        borderPane.setCenter(notificationsScene);
        notificationsController.initData(account);
        lblNotifications.setUnderline(true);
        lblHistory.setUnderline(false);
        lblMembership.setUnderline(false);
        lblBooks.setUnderline(false);
    }

    @FXML
    public void switchToBooks() throws IOException {
        borderPane.setCenter(booksScene);
        searchBooksMemberController.initData(account, borderPane, this);
        lblNotifications.setUnderline(false);
        lblHistory.setUnderline(false);
        lblMembership.setUnderline(false);
        lblBooks.setUnderline(true);
    }

    @FXML
    private void switchToHistory() throws IOException {
        borderPane.setCenter(historyScene);
        issuedBooksHistoryController.initData(account);
        lblNotifications.setUnderline(false);
        lblHistory.setUnderline(true);
        lblMembership.setUnderline(false);
        lblBooks.setUnderline(false);
    }

    @FXML
    private void switchToMembership() {
        borderPane.setCenter(membershipScene);
        membershipController.initData(account);
        lblNotifications.setUnderline(false);
        lblHistory.setUnderline(false);
        lblMembership.setUnderline(true);
        lblBooks.setUnderline(false);
    }

}
