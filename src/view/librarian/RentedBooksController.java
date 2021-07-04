package view.librarian;

import controller.IssuedBookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import model.enums.BookState;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import view.librarian.model.RentedBooksTable;

import java.io.IOException;
import java.time.LocalDate;

public class RentedBooksController implements Observer {
    public TableView<RentedBooksTable> rentedBooksTable;
    public Label returnFromRepair;
    public Label returnedLbl;
    public Label lostLbl;
    public Label repaitLbl;
    public Label returnFromRepairLbl;
    ObservableList<RentedBooksTable> dataRentedBooksTable;

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
        rentedBooksTable.setItems(getRentedBooks());


        TableColumn colMember = new TableColumn("Member") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.2));
            }
        };
        rentedBooksTable.getColumns().add(colMember);

        TableColumn colBook = new TableColumn("Book") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.2));
            }
        };
        rentedBooksTable.getColumns().add(colBook);

        TableColumn colIssuedDate = new TableColumn("Issued date") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.2));
            }
        };
        rentedBooksTable.getColumns().add(colIssuedDate);

        TableColumn colReturnDate = new TableColumn("Return date") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.2));
            }
        };
        rentedBooksTable.getColumns().add(colReturnDate);

        TableColumn colState = new TableColumn("State") {
            {
                prefWidthProperty().bind(rentedBooksTable.widthProperty().multiply(0.2));
            }
        };
        rentedBooksTable.getColumns().add(colState);

        colMember.setCellValueFactory(new PropertyValueFactory<RentedBooksTable, String>("member"));
        colBook.setCellValueFactory(new PropertyValueFactory<RentedBooksTable, String>("book"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<RentedBooksTable, Boolean>("issuedDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<RentedBooksTable, LocalDate>("returnDate"));
        colState.setCellValueFactory(new PropertyValueFactory<RentedBooksTable, String>("state"));

        returnFromRepairLbl.setOnMouseClicked(e-> {
//            cont.setStatus(rentedBooksTable.getSelectionModel().getSelectedItem().getBook(), BookState.AVAILABLE);
        });
        repaitLbl.setOnMouseClicked(e-> {
//            cont.setStatus(rentedBooksTable.getSelectionModel().getSelectedItem().getBook(), BookState.REPAIRING);
        });
        lostLbl.setOnMouseClicked(e-> {
//            cont.setStatus(rentedBooksTable.getSelectionModel().getSelectedItem().getBook(), BookState.LOST);
        });
        returnedLbl.setOnMouseClicked(e-> {
//            cont.setStatus(rentedBooksTable.getSelectionModel().getSelectedItem().getBook(), BookState.AVAILABLE);
        });
    }

    private ObservableList<RentedBooksTable> getRentedBooks() {
        ObservableList<RentedBooksTable> list = FXCollections.observableArrayList();
        for (IssuedBook issuedBook : library.getCurrentlyIssued()) {
            list.add(new RentedBooksTable(controller.getAuthorName(issuedBook), issuedBook.getTitle(), issuedBook.getIssueDate(), issuedBook.getReturnDate(), issuedBook.getBook().getState()));

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
