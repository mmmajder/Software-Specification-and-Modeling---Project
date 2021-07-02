package view.member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;
import observer.Observer;
import view.member.model.MemberHistoryTable;

import java.io.IOException;

public class HistoryController implements Observer {
    public TableView<MemberHistoryTable> historyTable;
    ObservableList<MemberHistoryTable> dataHistoryTable;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    public void initData(Account account) throws IOException {
        this.account = account;
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        //libraryRepo.loadIssuedBooks(library);

        historyTable.getColumns().add(new TableColumn("Title") {
            {
                prefWidthProperty().bind(historyTable.widthProperty().multiply(0.45));
            }
        });
        historyTable.getColumns().add(new TableColumn("Issue date") {
            {
                prefWidthProperty().bind(historyTable.widthProperty().multiply(0.2));
            }
        });
        historyTable.getColumns().add(new TableColumn("Return date") {
            {
                prefWidthProperty().bind(historyTable.widthProperty().multiply(0.2));
            }
        });
//        historyTable.getColumns().add(new TableColumn("Returned date") {
//            {
//                prefWidthProperty().bind(historyTable.widthProperty().multiply(0.2));
//            }
//        });
        historyTable.getColumns().add(new TableColumn("Status") {
            {
                prefWidthProperty().bind(historyTable.widthProperty().multiply(0.15));
            }
        });
        //historyTable.setItems(getHistory());
    }

    private ObservableList<MemberHistoryTable> getHistory() {
        ObservableList<MemberHistoryTable> list = FXCollections.observableArrayList();
//        for (IssuedBook issuedBook : library.getMembersReturnedBooks(account)) {
//            list.add(new MemberHistoryTable(issuedBook.getBook().getEdition().getTitle(), issuedBook.getIssueDate(), issuedBook.getReturnDate(), issuedBook.getState()));
//        }
        return list;
    }

    @Override
    public void updatePerformed() {
        libraryRepo.loadIssuedBooks(library);
        historyTable.setItems(getHistory());
    }
}
