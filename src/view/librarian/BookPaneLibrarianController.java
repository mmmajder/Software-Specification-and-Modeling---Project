package view.librarian;

import controller.EditionController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import model.Edition;
import model.Library;

public class BookPaneLibrarianController {
    public ImageView imageView;
    public Edition edition;
    public Label lblTitle;
    public Label lblAuthor;
    public Label lblAvailable;
    public Label lblShell;

    EditionController editionController;
    Library library;

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition, Library library) {
        this.edition = edition;
        this.library = library;
        this.editionController = new EditionController(library);
//        try (InputStream in = new URL(edition.getImage()).openStream()) {
//            Files.copy(in, Paths.get("src/fxml/images/" + edition.getTitle() + ".jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //TODO: ucitavanje slike
        //ImageView image = new ImageView(new Image(getClass().getResourceAsStream("analog.png")));
        //this.imageView = new ImageView(new Image("src/fxml/images/" + edition.getTitle() + ".jpg", 150, 150, false, true));
        lblTitle.setText(edition.getTitle());
        //Image image = new Image(getClass().getResourceAsStream(edition.getImage()));
        //imageView.setImage(image);
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


