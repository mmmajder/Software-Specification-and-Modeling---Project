package view.member;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Edition;
import view.member.BookPane;

import java.util.ArrayList;
import java.util.List;

public class SearchBooksMemberController {
    public ListView<String> genres;
    public List<BookPane> bookPanes;
    public GridPane grid;
    private Scene bookScene;

    public void setSecondScene(Scene scene) {
        this.bookScene = scene;
    }

    public void switchToBook(MouseEvent actionEvent, Edition edition) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(bookScene);
        //bookScene.initData(edition);
    }

    public void initData() {
        setGenres();
        this.bookPanes = new ArrayList<BookPane>();
        for (int i = 0; i < 12; i++) {
            bookPanes.add(new BookPane());
        }
        setBookPanes();
    }

    @FXML
    public void setGenres() {
        genres.getItems().add("GENRES");
//        for (Genre genre : library.getGenres()) {
//            genres.getItems().add(genre.getName());
//        }
    }

    @FXML
    public void setBookPanes() {
        int i = 0;
        for (BookPane bookPane : bookPanes) {
            bookPane.getStyleClass().add("bookPane");
            bookPane.setVisible(true);
            bookPane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> switchToBook(mouseEvent, bookPane.getEdition()));
            grid.add(bookPane, i % 3, i % 4);
            i++;
        }
    }

    @FXML
    public void updateBooks(List<Edition> editions, int page) {
        for (int i = page * 12; i < page * 13; i++) {
            if (editions.size() <= i) {
                bookPanes.get(i % 12).setVisible(false);
            } else {
                bookPanes.get(i % 12).setEdition(editions.get(i));
            }
        }
    }
}
