package view.librarian;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class BookRentController {

    public Button rentBtn;
    public TextField userIdLbl;
    public TextField sampleIdLbl;


    public void rentBook(MouseEvent event) {
        try {
//            controller.rentBook(userIdLbl.getText(), sampleIdLbl.getText());
        } catch (Exception e) {
            // add exceptions
            /*Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Alert");
        a.setContentText("Use");*/
        }
    }
}
