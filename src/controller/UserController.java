package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Edition;
import model.enums.Genre;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    public ListView<String> genres;
    public List<BookPane> bookPanes;
    public GridPane grid;

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
        for (Pane bookPane: bookPanes) {
            bookPane.getStyleClass().add("bookPane");
            bookPane.setVisible(true);
            grid.add(bookPane,i%3, i%4);
            i++;
        }
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
