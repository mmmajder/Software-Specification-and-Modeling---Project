package view.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;

import java.io.IOException;

public class HistoryController {
    public TableView<HistoryData> table;
    public TableColumn title;
    public TableColumn issueDate;
    public TableColumn returnDate;
    public TableColumn status;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    @FXML
    private void initData(Account account) throws IOException {
        this.account = account;
        this.library = new Library();
        libraryRepo = new LibraryRepo();
//        libraryRepo.loadIssuedBooks(library);
        table.setItems(getData());

    }
    public ObservableList<HistoryData> getData(){
        ObservableList<HistoryData> list = FXCollections.observableArrayList();
//        for (IssuedBook issuedBook:library.getIssuedBooks()) {
//            list.add(new HistoryData(issuedBook.getBook().getEdition().getTitle(), issuedBook.getIssueDate(), issuedBook.getReturnDate(), issuedBook.getBook().getState()));
//        }
        return list;
    }
}
