package view.member;

import controller.IssuedBookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;
import observer.Observer;
import view.member.model.MemberHistoryTable;

import java.io.IOException;

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
        libraryRepo.loadIssuedBooks(library);

        issuedBooksHistoryTable.getColumns().add(new TableColumn("Title") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.45));
            }
        });
        issuedBooksHistoryTable.getColumns().add(new TableColumn("Issue date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.2));
            }
        });
        issuedBooksHistoryTable.getColumns().add(new TableColumn("Return date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.2));
            }
        });
        issuedBooksHistoryTable.getColumns().add(new TableColumn("Returned date") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.2));
            }
        });
        issuedBooksHistoryTable.getColumns().add(new TableColumn("Status") {
            {
                prefWidthProperty().bind(issuedBooksHistoryTable.widthProperty().multiply(0.15));
            }
        });
        issuedBooksHistoryTable.setItems(getHistory());
    }

    private ObservableList<MemberHistoryTable> getHistory() {
        ObservableList<MemberHistoryTable> list = FXCollections.observableArrayList();
        for (IssuedBook issuedBook : library.getMembersReturnedBooks(account)) {
            list.add(new MemberHistoryTable(issuedBook.getBook().getEdition().getTitle(), issuedBook.getIssueDate(),
                    issuedBook.getReturnDate(), controller.calculateReturnDate(issuedBook),
                    controller.getIssuedBookState(issuedBook)));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        this.library = new Library();
        libraryRepo.loadIssuedBooks(library);
        issuedBooksHistoryTable.setItems(getHistory());
    }
}
