package controller;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Edition;

import java.awt.*;

public class BookPane extends Pane {
    private ImageView imageView;
    private Edition edition;
    private Label title;
    private Label author;
    private Label available;

    public BookPane() {
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
        //this.imageView = new ImageView(new Image(getClass().getResourceAsStream(edition.getImageUrl())));
        //title.setText(edition.getTitle());
        //author.setText(edition.getAuthor());
        boolean isAvailable = false;
        if (isAvailable) {
            available.setText("AVAILABLE");
            // NE RADI
            //available.setTextFill(Color.green);
        } else {
            available.setText("NOT AVAILABLE");
            //available.setTextFill(Color.red);
        }
    }
}
