package view.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.*;
import observer.Observer;
import view.librarian.model.MemberTable;
import view.librarian.model.RentedBooksTable;

import java.io.IOException;

public class RentedBooksController implements Observer {
    public TableView<RentedBooksTable> rentedBooksTable;
    ObservableList<RentedBooksTable> dataRentedBooksTable;

    Library library;
    ILibraryRepo libraryRepo;

    public void initData() throws IOException {
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadIssuedBooks(library);
        rentedBooksTable.setItems(getRentedBooks());
    }

    private ObservableList<RentedBooksTable> getRentedBooks() {
        ObservableList<RentedBooksTable> list = FXCollections.observableArrayList();
        for (IssuedBook issuedBook : library.getCurrentlyIssued()) {
            list.add(new RentedBooksTable(issuedBook.getMember().getName()+" "+issuedBook.getMember().getSurname(), issuedBook.getBook().getEdition().getTitle(), issuedBook.getIssueDate(), issuedBook.getReturnDate()));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        libraryRepo.loadIssuedBooks(library);
//        rentedBooksTable.setItems(getRentedBooks());
    }
}
