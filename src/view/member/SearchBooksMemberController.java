package view.member;

import controller.SearchBooksController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;
import observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchBooksMemberController implements Observer {
    public ListView<String> genres;
    public List<BookPane> bookPanes;
    public GridPane grid;
    public TextField search;
    public Scene scene;
    public List<Edition> currentEditions;
    public int currentPage;
    public Label titleSort;
    public Label publishedDateSort;
    public ImageView ascSort;
    public ImageView descSort;
    public Label addEdition;

    ILibraryRepo libraryRepo;
    Library library;
    Account account;
    private SearchBooksController controller;

    public void switchToBook(MouseEvent actionEvent, Edition edition) throws IOException {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/member/reservationMember.fxml"));
        Scene bookScene = bookLoader.load();
        primaryStage.setScene(bookScene);
        BookMemberController bookMemberController = bookLoader.getController();
        bookMemberController.setSecondScene(this.scene);
        bookMemberController.initData(edition);
    }

    public void initData(Account account, Scene mainScene) {
        library = new Library();
        libraryRepo = new LibraryRepo();
        this.controller = new SearchBooksController(library);
        this.account = account;
        libraryRepo.loadEditions(library);
        libraryRepo.loadContributors(library);
        libraryRepo.loadContributorRoles(library);
        this.library.addObserver(this);
        this.scene = mainScene;

        setGenres();
        this.bookPanes = new ArrayList<BookPane>();
        for (int i = 0; i < 12; i++) {
            bookPanes.add(new BookPane());
        }
        //currentEditions = library.getRandomEditions();
        setBookPanes();

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            updateBooks(library.filterEditions(currentEditions, newValue));
        });

        genres.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                updateBooks(library.filterByGenre(currentEditions, newValue)));

        ascSort.setPickOnBounds(true);
        ascSort.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //updateBooks(library.sortAsc(currentEditions));
        });
        descSort.setPickOnBounds(true);
        descSort.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //updateBooks(library.sortDesc(currentEditions));
        });

        titleSort.textProperty().addListener((ov, t, t1) -> controller.sortByTitleAsc());
        publishedDateSort.textProperty().addListener((ov, t, t1) -> controller.sortByPublishedDateAsc());
    }

    @FXML
    public void setGenres() {
        genres.getItems().add("GENRES");
        for (Genre genre : library.getGenres()) {
            genres.getItems().add(genre.getName());
        }
    }

    @FXML
    public void setBookPanes() {
        int i = 0;
        for (BookPane bookPane : bookPanes) {
            bookPane.getStyleClass().add("bookPane");
            bookPane.setEdition(currentEditions.get(i));
            bookPane.setVisible(true);
            bookPane.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
                try {
                    switchToBook(mouseEvent, bookPane.getEdition());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            grid.add(bookPane, i % 3, i % 4);
            i++;
        }
    }

    public void updateBooks(List<Edition> editions) {
        this.currentEditions = editions;
        int i = 0;
        for (BookPane bookPane : bookPanes) {
            bookPane.setEdition(currentEditions.get(i));
            i++;
        }
        //repaint
    }

    @FXML
    public void changePage(List<Edition> editions, int page) {
        for (int i = page * 12; i < page * 13; i++) {
            if (editions.size() <= i) {
                bookPanes.get(i % 12).setVisible(false);
            } else {
                bookPanes.get(i % 12).setEdition(editions.get(i));
            }
        }
    }

    @Override
    public void updatePerformed() {
        int i = 0;
        for (BookPane bookPane : bookPanes) {
            bookPane.setEdition(library.getEditions().get(i));
            i++;
        }
    }

    @FXML
    public void switchToAdditionOfEdition(MouseEvent event) {

    }
}
