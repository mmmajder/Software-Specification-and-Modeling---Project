package view.member;

import controller.EditionController;
import controller.SearchBooksController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import model.*;

import java.io.IOException;
import java.util.List;

public class SearchBooksMemberController {
    public ListView<String> genres;
    public TilePane tilePane;
    public TextField search;
    public Label titleSort;
    public Label publishedDateSort;
    public BorderPane borderPane;
    public BorderPane left;
    public AnchorPane right;

    public List<Edition> currentEditions;
    public BorderPane mainBorderPane;
    public ScrollPane scrollPane;
    public AnchorPane anchorPane;

    ILibraryRepo libraryRepo;
    Library library;
    Account account;
    EditionController editionController;
    MemberController memberController;

    SearchBooksController searchBooksController;

    public void switchToBook(Edition edition) throws IOException {
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/member/reservationMember.fxml"));
        Parent bookScene = bookLoader.load();
        BookMemberController bookMemberController = bookLoader.getController();
        bookMemberController.initData(edition, memberController, account);
        mainBorderPane.setCenter(bookScene);
    }

    public void initData(Account account, BorderPane mainBorderPane, MemberController memberController) {
        library = new Library();
        searchBooksController = new SearchBooksController(library);
        libraryRepo = new LibraryRepo();
        this.account = library.getAccountByEmail(account.getEmail());
        this.memberController = memberController;
        libraryRepo.loadEditions(library);
        libraryRepo.loadContributors(library);
        libraryRepo.loadContributorRoles(library);
        libraryRepo.loadGenres(library);

        editionController = new EditionController(library);
        this.mainBorderPane = mainBorderPane;

        tilePane = new TilePane();
        tilePane.setStyle("-fx-background-color: #63ac29;");
        scrollPane.setContent(tilePane);
        tilePane.prefWidthProperty().bind(scrollPane.widthProperty());
        tilePane.prefHeightProperty().bind(scrollPane.heightProperty());
        tilePane.setPrefColumns(4);
        tilePane.setTileAlignment(Pos.TOP_LEFT);
        tilePane.setHgap(8);
        tilePane.setVgap(8);
        tilePane.setPadding(new Insets(5));

        currentEditions = library.getEditions();
        initializeEditions(currentEditions);
        setGenres();

        search.textProperty().addListener((observable, oldValue, newValue) -> initializeEditions(
                searchBooksController.filterByTitle(currentEditions, newValue)));

        genres.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                initializeEditions(searchBooksController.filterByGenre(currentEditions, newValue)));
    }

    @FXML
    public void sortByTitle() {
        initializeEditions(searchBooksController.sortByTitleAsc(currentEditions));
    }

    @FXML
    public void sortByPublishedDate() {
        initializeEditions(searchBooksController.sortByPublishedDateAsc(currentEditions));
    }

    @FXML
    public void setGenres() {
        genres.getItems().clear();
        genres.getItems().add("GENRES");
        genres.getItems().addAll(searchBooksController.getGenreNamesSorted());
    }

    public void initializeEditions(List<Edition> editions) {
        try {
            tilePane.getChildren().clear();
            for (Edition edition : editions) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../../fxml/member/bookSample.fxml"));
                AnchorPane bookPane = fxmlLoader.load();
                bookPane.setPrefWidth(300);
                bookPane.setPrefHeight(130);
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
                itemController.setEdition(edition, library);
                tilePane.getChildren().add(bookPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
