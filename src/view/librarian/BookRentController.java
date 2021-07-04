package view.librarian;

import controller.RentingController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.Account;
import model.Librarian;
import model.Library;

public class BookRentController {

    public Button rentBtn;
    public TextField userIdLbl;
    public TextField sampleIdLbl;
    public Account account;
    public Library library;

    public BookRentController(Account account) {
        this.account = account;
    }

    public void rentBook(MouseEvent event) {
        try {
            library = new Library(); // add
            RentingController rentingController = new RentingController(library);
            rentingController.rent(userIdLbl.getText(), sampleIdLbl.getText(), account.getPerson());
        } catch (Exception e) {
            // add exceptions
            /*Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Alert");
        a.setContentText("Use");*/
        }
    }
}
