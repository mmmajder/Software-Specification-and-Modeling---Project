package view.member;

import controller.EditionController;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import model.Edition;
import model.Library;

public class BookPaneController {
    public Edition edition;
    public Label lblTitle;
    public Label lblAuthor;
    public Label lblAvailable;

    Library library;
    EditionController editionController;

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition, Library library) {
        this.edition = edition;
        this.library = library;
        this.editionController = new EditionController(library);
        lblTitle.setText(edition.getTitle());
        lblAuthor.setText(editionController.getAuthorName(edition));

        if (library.isAvailable(edition)) {
            lblAvailable.setText("AVAILABLE");
            lblAvailable.setTextFill(Paint.valueOf("#abc82e"));
        } else {
            lblAvailable.setText("NOT AVAILABLE");
            lblAvailable.setTextFill(Paint.valueOf("#CD113B"));
        }
    }
}


