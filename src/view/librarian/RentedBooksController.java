package view.librarian;

import controller.IssuedBookController;
import controller.ReturnController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.NoCurrentlyIssuedBookWithThatIdException;
import view.librarian.model.RentedBooksTable;

import java.io.IOException;
import java.time.LocalDate;

public class RentedBooksController implements Observer {
    public TableView<RentedBooksTable> rentedBooksTable;

    Library library;
    ILibraryRepo libraryRepo;
    IssuedBookController controller;

    public void initData() throws IOException {
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        library.addObserver(this);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadIssuedBooks(library);
        libraryRepo.loadContributors(library);
        libraryRepo.loadContributorRoles(library);
        controller = new IssuedBookController(library);

        rentedBooksTable.getColumns().clear();
        TableColumn<RentedBooksTable, String> colMember = new TableColumn<RentedBooksTable, String>("Member") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.2));
            }
        };
        rentedBooksTable.getColumns().add(colMember);

        TableColumn<RentedBooksTable, String> colBookID = new TableColumn<RentedBooksTable, String>("Book ID") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.1));
            }
        };
        rentedBooksTable.getColumns().add(colBookID);

        TableColumn<RentedBooksTable, String> colBook = new TableColumn<RentedBooksTable, String>("Book") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.3));
            }
        };
        rentedBooksTable.getColumns().add(colBook);

        TableColumn<RentedBooksTable, LocalDate> colIssuedDate = new TableColumn<RentedBooksTable, LocalDate>("Issued date") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.1));
            }
        };
        rentedBooksTable.getColumns().add(colIssuedDate);

        TableColumn<RentedBooksTable, LocalDate> colReturnDate = new TableColumn<RentedBooksTable, LocalDate>("Return date") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.1));
            }
        };
        rentedBooksTable.getColumns().add(colReturnDate);

        TableColumn<RentedBooksTable, String> colState = new TableColumn<RentedBooksTable, String>("State") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.2));
            }
        };
        rentedBooksTable.getColumns().add(colState);
        rentedBooksTable.setItems(getRentedBooks());

        colMember.setCellValueFactory(new PropertyValueFactory<>("member"));
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colBook.setCellValueFactory(new PropertyValueFactory<>("book"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<>("issuedDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colState.setCellValueFactory(new PropertyValueFactory<>("state"));
    }

    private ObservableList<RentedBooksTable> getRentedBooks() {
        ObservableList<RentedBooksTable> list = FXCollections.observableArrayList();
        for (IssuedBook issuedBook : library.getCurrentlyIssued()) {
            list.add(new RentedBooksTable(issuedBook.getMember().getFullName(), issuedBook.getBookId(), issuedBook.getTitle(),
                    issuedBook.getIssueDate(), issuedBook.getReturnDate(), issuedBook.getBook().getState()));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        rentedBooksTable.setItems(getRentedBooks());
    }

    public void setReturned(MouseEvent mouseEvent) {
        RentedBooksTable rent = rentedBooksTable.getSelectionModel().getSelectedItem();
        ReturnController returnController = new ReturnController(library);
        try {
            returnController.returnBook(rent.getBookID());
        } catch (NoCurrentlyIssuedBookWithThatIdException e) {
            createAlert("There is no book with that id");
        }
    }

    private void createAlert(String text) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Alert");
        a.setContentText(text);
        a.show();
    }
}
