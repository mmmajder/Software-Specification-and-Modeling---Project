package view.member;

import controller.CRUDController;
import controller.IssuedBookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import view.member.model.MemberHistoryTable;

import java.io.IOException;
import java.time.LocalDate;

public class IssuedBooksHistoryController implements Observer {
    public TableView<MemberHistoryTable> issuedBooksHistoryTable;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;
    private IssuedBookController controller;

    public void initData(Account account) throws IOException {
        this.account = account;
        this.library = new Library();
        controller = new IssuedBookController(library);
        libraryRepo = new LibraryRepo();
        library.addObserver(this);
        libraryRepo.loadContributors(library);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadIssuedBooks(library);
        libraryRepo.loadMaxIssueDays(library);
        this.account = library.getAccountByEmail(account.getEmail());

        issuedBooksHistoryTable.getColumns().clear();
        TableColumn<MemberHistoryTable, String> colId = new TableColumn<MemberHistoryTable, String>("ID") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.1));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colId);


        TableColumn<MemberHistoryTable, String> colTitle = new TableColumn<MemberHistoryTable, String>("Title") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colTitle);

        TableColumn<MemberHistoryTable, LocalDate> colIssuedDate = new TableColumn<MemberHistoryTable, LocalDate>("Issue date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colIssuedDate);

        TableColumn<MemberHistoryTable, LocalDate> colReturnDate = new TableColumn<MemberHistoryTable, LocalDate>("Return date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colReturnDate);

        TableColumn<MemberHistoryTable, LocalDate> colReturnedDate = new TableColumn<MemberHistoryTable, LocalDate>("Returned date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colReturnedDate);

        TableColumn<MemberHistoryTable, Boolean> colProlonged = new TableColumn<MemberHistoryTable, Boolean>("Prolonged") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colProlonged);

        TableColumn<MemberHistoryTable, String> colStatus = new TableColumn<MemberHistoryTable, String>("Status") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colStatus);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        colProlonged.setCellValueFactory(new PropertyValueFactory<>("prolonged"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("state"));

        issuedBooksHistoryTable.setItems(getHistory());
    }

    private ObservableList<MemberHistoryTable> getHistory() {
        ObservableList<MemberHistoryTable> list = FXCollections.observableArrayList();
        for (IssuedBook issuedBook : library.getMembersReturnedBooks(account)) {

            list.add(new MemberHistoryTable(issuedBook.getBook().getBookId(), issuedBook.getBook().getEdition().getTitle(),
                    issuedBook.getIssueDate(), controller.calculateReturnDate(issuedBook), issuedBook.getReturnDate(),
                    issuedBook.isProlongedIssue(), "Returned"));
        }
        for (IssuedBook issuedBook : library.getMembersCurrentlyTakenBooks(account)) {
            list.add(new MemberHistoryTable(issuedBook.getBook().getBookId(), issuedBook.getBook().getEdition().getTitle(),
                    issuedBook.getIssueDate(), controller.calculateReturnDate(issuedBook), null,
                    issuedBook.isProlongedIssue(), "Taken"));
        }
        for (Reservation reservation : library.getReservations()) {
            list.add(new MemberHistoryTable(reservation.getBook().getBookId(), reservation.getBook().getEdition().getTitle(), null,
                    null, null, null, "Reserved"));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        issuedBooksHistoryTable.setItems(getHistory());
    }

    public void prolong() {
        MemberHistoryTable memberHistoryTable = issuedBooksHistoryTable.getSelectionModel().getSelectedItem();
        CRUDController crudController = new CRUDController(library);
        crudController.prolongIssue(account.getPerson().getJMBG(), memberHistoryTable.getId(), memberHistoryTable.getIssueDate());
    }
}
