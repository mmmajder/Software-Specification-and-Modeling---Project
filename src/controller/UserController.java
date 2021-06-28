package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Edition;
import model.enums.Genre;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    public ListView<String> genres;
    public List<BookPane> bookPanes;
    public GridPane grid;
    private Stage stage;
    private Scene scene;

    public void initData(){
        setGenres();
        this.bookPanes = new ArrayList<BookPane>();
        for (int i=0; i<12; i++) {
            bookPanes.add(new BookPane());
        }
        setBookPanes();
    }

    @FXML
    public void setGenres() {
        genres.getItems().add("GENRES");
        for (Genre genre: Genre.values()) {
            genres.getItems().add(genre.label);
        }
    }

    @FXML
    public void setBookPanes() {
        int i = 0;
        for (BookPane bookPane: bookPanes) {
            bookPane.getStyleClass().add("bookPane");
            bookPane.setVisible(true);
            bookPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        switchToBook(bookPane.getEdition());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            grid.add(bookPane,i%3, i%4);
            i++;
        }
    }

    private void switchToBook(Edition edition) throws IOException {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/bookMember.fxml"));
        final Parent root = (Parent) loader.load();
        final BookController controller = loader.getController();
        controller.initData(edition);
        stage = (Stage) (scene.getWindow());
        scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void updateBooks(List<Edition> editions, int page) {
        for (int i=page*12; i<page*13; i++) {
            if (editions.size() <= i) {
                bookPanes.get(i % 12).setVisible(false);
            } else {
                bookPanes.get(i % 12).setEdition(editions.get(i));
            }
        }
    }
}
