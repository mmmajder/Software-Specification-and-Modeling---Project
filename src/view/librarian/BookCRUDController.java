package view.librarian;

import fxml.librarian.model.BookEditionTable;
import fxml.librarian.model.BookSampleTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import model.*;

import java.io.IOException;

public class BookCRUDController {
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
    Account account;
    ILibraryRepo libraryRepo;

    @FXML
    private void initData(Account account, Edition edition) throws IOException {
        this.edition = edition;
        this.account = account;
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadBooks(library);
        editionTable.setItems(getSamples());

        editionTable.setOnMouseClicked(e -> {
            loadSamples();
        });

    }

    private ObservableList<BookEditionTable> getSamples() {
        ObservableList<BookEditionTable> list = FXCollections.observableArrayList();
        for (Book book : edition.getBooks()) {
            list.add(new BookEditionTable(book.getBookId(), book.getState(), book.getIsRestricted()));
        }
        return list;
    }

    private void loadSamples() {
        dataSampleTable.clear();
        for (BookEditionTable row : editionTable.getSelectionModel().getSelectedItems()) {
            for (int i = 1; i <= 1; i++) {
                Book sample = library.getBook(row.getBookId());
                for (IssuedBook issuedBook : sample.getIssueHistory()) {
//                    dataSampleTable.add(new BookSampleTable(issuedBook.getIssueDate(), issuedBook.getReturnedDate(), issuedBook, issuedBook.getMember().getName() + " " + issuedBook.getMember().getSurname()))
                }
            }
        }
    }
}
