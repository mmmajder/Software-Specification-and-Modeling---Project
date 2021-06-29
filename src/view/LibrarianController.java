package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrarianController {
    public Label lblReservations;
    public Label lblBooks;
    public Label lblRentedBooks;
    public Label lblMembers;
    public Label lblUsername;

    public BorderPane borderPane;

    public Parent booksScene;
    public Parent bookScene;
    public Parent historyScene;
    public Parent membershipScene;
    public Parent notificationsScene;

    public void initData() throws IOException {
        FXMLLoader booksLoader = new FXMLLoader(getClass().getResource("../fxml/booksMember.fxml"));
        booksScene = booksLoader.load();
        BooksMemberController booksMemberController = (BooksMemberController) booksLoader.getController();


        //FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../fxml/bookMember.fxml"));
        //bookScene = bookLoader.load();
        //BookMemberController bookMemberController = (BookMemberController) bookLoader.getController();

        //bookMemberController.setSecondScene(new Scene(booksScene));
        //booksMemberController.setSecondScene(new Scene(bookScene));

        FXMLLoader historyLoader = new FXMLLoader(getClass().getResource("../fxml/history.fxml"));
        historyScene = historyLoader.load();
        //HistoryController historyController = (HistoryController) historyLoader.getController();

        FXMLLoader membershipLoader = new FXMLLoader(getClass().getResource("../fxml/membership.fxml"));
        membershipScene = membershipLoader.load();
        //MembershipController membershipController = (MembershipController) membershipLoader.getController();

        FXMLLoader notificationsLoader = new FXMLLoader(getClass().getResource("../fxml/notifications.fxml"));
        notificationsScene = notificationsLoader.load();
        //NotificationsController notificationsController = (NotificationsController) notificationsLoader.getController();

        //switchToBooks();
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

//    @FXML
//    private void switchToNotifications(MouseEvent event) {
//        borderPane.setCenter(notificationsScene);
//        lblNotifications.setUnderline(true);
//        lblHistory.setUnderline(false);
//        lblMembership.setUnderline(false);
//        lblBooks.setUnderline(false);
//    }
//
//    @FXML
//    private void switchToBooks(MouseEvent event) throws IOException {
//        switchToBooks();
//    }
//
//    public void switchToBooks() {
//        borderPane.setCenter(booksScene);
//        lblNotifications.setUnderline(false);
//        lblHistory.setUnderline(false);
//        lblMembership.setUnderline(false);
//        lblBooks.setUnderline(true);
//    }
//
//    @FXML
//    private void switchToHistory(MouseEvent event) {
//        borderPane.setCenter(historyScene);
//        lblNotifications.setUnderline(false);
//        lblHistory.setUnderline(true);
//        lblMembership.setUnderline(false);
//        lblBooks.setUnderline(false);
//    }
//
//    @FXML
//    private void switchToMembership(MouseEvent event) {
//        borderPane.setCenter(membershipScene);
//        lblNotifications.setUnderline(false);
//        lblHistory.setUnderline(false);
//        lblMembership.setUnderline(true);
//        lblBooks.setUnderline(false);
//    }

}
