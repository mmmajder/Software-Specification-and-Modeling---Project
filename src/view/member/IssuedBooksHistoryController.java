package view.member;

import controller.IssuedBookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import observer.Observer;
import view.member.model.MemberHistoryTable;

import java.io.IOException;
import java.time.LocalDate;

public class IssuedBooksHistoryController implements Observer {
    public TableView<MemberHistoryTable> issuedBooksHistoryTable;
    ObservableList<MemberHistoryTable> dataHistoryTable;
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

        TableColumn colTitle = new TableColumn("Title") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.2));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colTitle);

        TableColumn colIssuedDate = new TableColumn("Issue date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.2));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colIssuedDate);

        TableColumn colReturnDate = new TableColumn("Return date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colReturnDate);

        TableColumn colReturnedDate = new TableColumn("Returned date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };

        TableColumn colProlonged = new TableColumn("Prolonged") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        };

        issuedBooksHistoryTable.getColumns().add(colReturnedDate);

        TableColumn colStatus = new TableColumn("Status") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.45));
            }
        };
        issuedBooksHistoryTable.getColumns().add(colStatus);

        colTitle.setCellValueFactory(new PropertyValueFactory<MemberHistoryTable, String>("bookTitle"));
        colIssuedDate.setCellValueFactory(new PropertyValueFactory<MemberHistoryTable, LocalDate>("issueDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<MemberHistoryTable, LocalDate>("returnDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<MemberHistoryTable, LocalDate>("returnedDate"));
        colProlonged.setCellValueFactory(new PropertyValueFactory<MemberHistoryTable, LocalDate>("prolonged"));
        colStatus.setCellValueFactory(new PropertyValueFactory<MemberHistoryTable, String>("state"));

        issuedBooksHistoryTable.setItems(getHistory());
    }

    private ObservableList<MemberHistoryTable> getHistory() {
        ObservableList<MemberHistoryTable> list = FXCollections.observableArrayList();
        System.out.println(library.getMembersReturnedBooks(account));
        for (IssuedBook issuedBook : library.getMembersReturnedBooks(account)) {
            list.add(new MemberHistoryTable(issuedBook.getBook().getEdition().getTitle(), issuedBook.getIssueDate(),
                    issuedBook.getReturnDate(), controller.calculateReturnDate(issuedBook), issuedBook.isProlongedIssue(),
                    "Returned"));
        }
        System.out.println(library.getMembersCurrentlyTakenBooks(account));
        for (IssuedBook issuedBook : library.getMembersCurrentlyTakenBooks(account)) {
            list.add(new MemberHistoryTable(issuedBook.getBook().getEdition().getTitle(), issuedBook.getIssueDate(),
                    issuedBook.getReturnDate(), null, issuedBook.isProlongedIssue(),
                    "Taken"));
        }
        System.out.println(library.getReservations());
        for (Reservation reservation : library.getReservations()) {
            list.add(new MemberHistoryTable(reservation.getBook().getEdition().getTitle(), null,
                    null, null, null, "Reserved"));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
//        this.library = new Library();
//        libraryRepo.loadIssuedBooks(library);
        issuedBooksHistoryTable.setItems(getHistory());
    }
}
