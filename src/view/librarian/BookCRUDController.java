package view.librarian;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableView;
import model.Account;
import model.ILibraryRepo;
import model.Library;
import model.LibraryRepo;

import java.io.IOException;

public class BookCRUDController {
    public TableView editionTable;
    public TreeTableView sampleTable;

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
