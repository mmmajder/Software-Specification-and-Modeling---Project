package view.member;

import controller.EditionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import model.*;

import java.io.IOException;
import java.util.List;

public class SearchBooksMemberController {
    public ListView<String> genres;
    public TilePane tilePane;
    public TextField search;
    public Label titleSort;
    public Label publishedDateSort;
    public ImageView ascSort;
    public ImageView descSort;
    public BorderPane borderPane;
    public BorderPane left;
    public BorderPane right;

    public List<Edition> currentEditions;
    public BorderPane mainBorderPane;
    public ScrollPane scrollPane;
    public AnchorPane anchorPane;

    ILibraryRepo libraryRepo;
    Library library;
    Account account;
    EditionController editionController;
    MemberController memberController;

    public void switchToBook(Edition edition) throws IOException {
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/member/reservationMember.fxml"));
        Parent bookScene = bookLoader.load();
        BookMemberController bookMemberController = bookLoader.getController();
        bookMemberController.initData(edition, memberController);
        mainBorderPane.setCenter(bookScene);
    }

    public void initData(Account account, BorderPane mainBorderPane, MemberController memberController) throws IOException {
        library = new Library();
        libraryRepo = new LibraryRepo();
        this.account = account;
        this.memberController = memberController;
        libraryRepo.loadEditions(library);
        libraryRepo.loadContributors(library);
        libraryRepo.loadContributorRoles(library);
        editionController = new EditionController(library);
        left.setPrefWidth(200);
        this.mainBorderPane = mainBorderPane;
        left.prefHeightProperty().bind(mainBorderPane.heightProperty());
        right.prefWidthProperty().bind(mainBorderPane.widthProperty());
        right.prefHeightProperty().bind(mainBorderPane.heightProperty());

        tilePane = new TilePane();
//        ColumnConstraints column1 = new ColumnConstraints();
//        column1.setPercentWidth(30);
//        column1.setHgrow(Priority.ALWAYS);
//        ColumnConstraints column2 = new ColumnConstraints();
//        column2.setPercentWidth(30);
//        column2.setHgrow(Priority.ALWAYS);
//        ColumnConstraints column3 = new ColumnConstraints();
//        column3.setPercentWidth(30);
//        column3.setHgrow(Priority.ALWAYS);
//        grid.getColumnConstraints().addAll(column1, column2, column3);
//        grid.prefWidthProperty().bind(scrollPane.widthProperty());
//        grid.prefHeightProperty().bind(scrollPane.heightProperty());
        tilePane.setStyle("-fx-background-color: #63ac29;");
        scrollPane.setContent(tilePane);
        anchorPane.prefWidthProperty().bind(right.widthProperty());
        anchorPane.prefHeightProperty().bind(right.heightProperty());
        scrollPane.prefWidthProperty().bind(anchorPane.widthProperty());
        scrollPane.prefHeightProperty().bind(anchorPane.heightProperty());
        tilePane.prefWidthProperty().bind(scrollPane.widthProperty());
        tilePane.prefHeightProperty().bind(scrollPane.heightProperty());
        tilePane.setPrefColumns(4);
        tilePane.setTileAlignment(Pos.TOP_LEFT);
        tilePane.setHgap(8);
        tilePane.setVgap(8);
        tilePane.setPadding(new Insets(5));

        currentEditions = editionController.getRandomEditions(50);
        initializeEditions(currentEditions);
        setGenres();

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
        genres.getItems().clear();
        genres.getItems().add("GENRES");
        for (Genre genre : library.getGenres()) {
            genres.getItems().add(genre.getName());
        }
    }

    public void initializeEditions(List<Edition> editions) {
        try {
            for (Edition edition : editions) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../../fxml/member/bookSample.fxml"));
                AnchorPane bookPane = fxmlLoader.load();
                bookPane.setPrefWidth(150);
                bookPane.setPrefHeight(100);
                bookPane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                    try {
                        switchToBook(edition);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                bookPane.setStyle("-fx-background-color: white;" +
                        "-fx-background-radius: 5.0;" +
                        "-fx-effect: dropShadow(three-pass-box,rgba(0,0,0,0.1), 10.0 , 0.0 , 0.0 ,10.0);");

                BookPaneController itemController = fxmlLoader.getController();
                itemController.setEdition(edition);
                tilePane.getChildren().add(bookPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
