package view.librarian;

import controller.RentingController;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Account;
import model.Librarian;
import model.Library;
import utils.exceptions.BookNotFoundException;
import utils.exceptions.BookRentingIsInvalidException;
import utils.exceptions.MemberNotFoundException;
import utils.exceptions.MemberUnableToRentException;

public class BookRentController {

    public TextField userIdLbl;
    public TextField sampleIdLbl;
    public Account account;
    public Library library;
    public Label confirmationMessage;

    public void initData(Account account, Library library) {
        this.account = account;
        this.library = library;
    }

    public void rentBook() {
        try {
            RentingController rentingController = new RentingController(library);
            rentingController.rent(userIdLbl.getText(), sampleIdLbl.getText(), account.getPerson());
            confirmationMessage.setText("Your rent was successful.");
        } catch (BookNotFoundException e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Alert");
            a.setContentText("There is no book with that ID");
            a.show();
        } catch (MemberNotFoundException e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Alert");
            a.setContentText("There is no member with that ID");
            a.show();
        } catch (BookRentingIsInvalidException e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Alert");
            a.setContentText("You can't rent that book");
            a.show();
        } catch (MemberUnableToRentException e) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Alert");
            a.setContentText("You have reached your maximum number of books");
            a.show();
            e.printStackTrace();
        }
    }
}