package view.member;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Edition;

public class BookPaneController {
    public ImageView imageView;
    public Edition edition;
    public Label lblTitle;
    public Label lblAuthor;
    public Label lblAvailable;

    public Edition getEdition() {
        return edition;
    }

//    @FXML
//    private void click(MouseEvent mouseEvent) {
//        myListener.onClickListener(fruit);
//    }

    public void setEdition(Edition edition) {
        this.edition = edition;
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
        //lblAuthor.setText(edition.getAuthor());
        boolean isAvailable = false;

        if (isAvailable) {
            lblAvailable.setText("AVAILABLE");
            // NE RADI
            //available.setTextFill(Color.green);
        } else {
            lblAvailable.setText("NOT AVAILABLE");
            //available.setTextFill(Color.red);
        }
    }
}


