package view.member;

import controller.EditionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.List;

public class SearchBooksMemberController {
    public ListView<String> genres;
    public GridPane grid;
    public TextField search;
    public Label titleSort;
    public Label publishedDateSort;
    public ImageView ascSort;
    public ImageView descSort;
    public BorderPane borderPane;
    public BorderPane left;
    public VBox right;

    public List<Edition> currentEditions;

    ILibraryRepo libraryRepo;
    Library library;
    Account account;
    EditionController editionController;

    public void switchToBook(MouseEvent actionEvent, Edition edition) throws IOException {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/member/reservationMember.fxml"));
        Pane bookScene = bookLoader.load();
        primaryStage.setScene(bookScene);
        BookMemberController bookMemberController = bookLoader.getController();
        bookMemberController.initData(edition);
    }

    public void initData(Account account) throws IOException {
        library = new Library();
        libraryRepo = new LibraryRepo();
        this.account = account;
        libraryRepo.loadEditions(library);
        libraryRepo.loadContributors(library);
        libraryRepo.loadContributorRoles(library);
        editionController = new EditionController(library);
        left.setPrefWidth(200);

        currentEditions = editionController.getRandomEditions(50);
        initializeEditions(currentEditions);
        setGenres();
        BorderPane.setAlignment(left, Pos.TOP_LEFT);
        BorderPane.setAlignment(right, Pos.CENTER_RIGHT);

        search.textProperty().addListener((observable, oldValue, newValue) -> initializeEditions(library.filterEditions(currentEditions, newValue)));

        genres.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                initializeEditions(library.filterByGenre(currentEditions, newValue)));

        ascSort.setPickOnBounds(true);
        ascSort.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //initializeEditions(library.sortAsc(currentEditions));
        });
        descSort.setPickOnBounds(true);
        descSort.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //initializeEditions(library.sortDesc(currentEditions));
        });

        titleSort.textProperty().addListener((ov, t, t1) -> initializeEditions(library.sortByTitle(currentEditions)));
        publishedDateSort.textProperty().addListener((ov, t, t1) -> initializeEditions(library.sortByPublishedDate(currentEditions)));
    }

    @FXML
    public void setGenres() {
        genres.getItems().add("GENRES");
        for (Genre genre : library.getGenres()) {
            genres.getItems().add(genre.getName());
            System.out.println(genre.getName());
        }
    }

    public void initializeEditions(List<Edition> editions) {
        int column = 0;
        int row = 1;
        try {
            for (Edition edition : editions) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../../fxml/member/bookSample.fxml"));
                AnchorPane bookPane = fxmlLoader.load();
                bookPane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                    try {
                        switchToBook(mouseEvent, edition);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                BookPaneController itemController = fxmlLoader.getController();
                itemController.setEdition(edition);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(bookPane, column++, row);
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(bookPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
