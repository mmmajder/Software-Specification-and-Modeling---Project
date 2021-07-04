package view.librarian;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import observer.Observer;
import view.librarian.model.BookEditionTable;
import view.librarian.model.BookSampleTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;

import java.io.IOException;

public class BookCRUDController implements Observer {
    ObservableList<BookEditionTable> dataEditionTable;
    ObservableList<BookSampleTable> dataSampleTable;
    public TableView<BookEditionTable> editionTable;
    public TableView<BookSampleTable> sampleTable;
    public TableColumn id;
    public TableColumn state;
    public TableColumn restricted;
    public TableColumn issueDate;
    public TableColumn returnedDate;
    public TableColumn status;
    public TableColumn member;
    private Edition edition;
    Library library;
    ILibraryRepo libraryRepo;
    LibrarianController librarianController;
    BookLibrarianController editionController;
    BorderPane mainBorderPane;

    @FXML
    public void backToEdition() throws IOException {
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/bookLibrarian.fxml"));
        Parent bookScene = bookLoader.load();
        BookLibrarianController bookLibrarianController = bookLoader.getController();
        bookLibrarianController.initData(edition, mainBorderPane, librarianController);
        mainBorderPane.setCenter(bookScene);
    }

    public void initData(Edition edition, BorderPane mainBorderPane, LibrarianController librarianController) {
        this.edition = edition;
        this.librarianController = librarianController;
        this.mainBorderPane = mainBorderPane;
        this.library = new Library();
        library.addObserver(this);
        libraryRepo = new LibraryRepo();
        libraryRepo.loadContributors(library);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadIssuedBooks(library);
        editionTable.setItems(getSamples());
        editionTable.setOnMouseClicked(e -> {
            loadSamples();
        });
    }

    private ObservableList<BookEditionTable> getSamples() {
        ObservableList<BookEditionTable> list = FXCollections.observableArrayList();
        for (Book book : edition.getBooks()) {
            list.add(new BookEditionTable(book.getBookId(), book.getState(), book.isRestricted()));
        }
        return list;
    }

    private void loadSamples() {
        dataSampleTable.clear();
        for (BookEditionTable row : editionTable.getSelectionModel().getSelectedItems()) {
            for (int i = 1; i <= 1; i++) {
                Book sample = library.getBook(row.getBookId());
//                for (IssuedBook issuedBook : sample.getIssueHistory()) {
//                    dataSampleTable.add(new BookSampleTable(issuedBook.getIssueDate(), issuedBook.getReturnDate(), issuedBook.getState(), issuedBook.getMember().getName() + " " + issuedBook.getMember().getSurname()))
//                }
            }
        }
    }

    @Override
    public void updatePerformed() {
        editionTable.setItems(getSamples());
        dataSampleTable.clear();
        sampleTable.setItems(dataSampleTable);

    }
}
