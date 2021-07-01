package view.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.*;
import view.librarian.model.MemberTable;
import view.librarian.model.RentedBooksTable;

import java.io.IOException;

public class RentedBooksController {
    public TableView<RentedBooksTable> rentedBooksTable;
    ObservableList<RentedBooksTable> dataRentedBooksTable;

    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    @FXML
    public void initData(Account account) throws IOException {
        this.account = account;
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadIssuedBooks(library);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        rentedBooksTable.setItems(getRentedBooks());
    }

    private ObservableList<RentedBooksTable> getRentedBooks() {
        ObservableList<RentedBooksTable> list = FXCollections.observableArrayList();
//        for (IssuedBook issuedBook : library.getActiveIssues()) {
//            list.add(new RentedBooksTable(issuedBook.getMember().getName()+" "+issuedBook.getMember().getSurname(), issuedBook.getBook().getEdition().getTitle(), issuedBook.getIssueDate(), issuedBook.getReturnDate()));
//        }
        return list;
    }
}
