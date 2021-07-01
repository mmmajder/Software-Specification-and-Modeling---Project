package view.member;

import com.sun.xml.internal.bind.v2.TODO;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import model.Edition;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        try (InputStream in = new URL(edition.getImage()).openStream()) {
            Files.copy(in, Paths.get("src/fxml/images/" + edition.getTitle() + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: ucitavanje slike
        //ImageView image = new ImageView(new Image(getClass().getResourceAsStream("analog.png")));
        //this.imageView = new ImageView(new Image("src/fxml/images/" + edition.getTitle() + ".jpg", 150, 150, false, true));
        title.setText(edition.getTitle());
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
