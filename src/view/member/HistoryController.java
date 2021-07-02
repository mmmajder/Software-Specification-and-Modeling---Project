package view.member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import model.*;
import observer.Observer;
import utils.exceptions.PersonIsNotAMemberException;
import view.librarian.model.RentedBooksTable;
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
        libraryRepo.loadIssuedBooks(library);
        historyTable.setItems(getHistory());
    }

    private ObservableList<MemberHistoryTable> getHistory() {
        ObservableList<MemberHistoryTable> list = FXCollections.observableArrayList();
        try {
            for (IssuedBook issuedBook : library.getMemberIssueHistory(account)) {
//                list.add(new MemberHistoryTable(issuedBook.getBook().getEdition().getTitle(), issuedBook.getIssueDate(), issuedBook.getReturnDate(), issuedBook.getState()));
            }
        } catch (PersonIsNotAMemberException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        libraryRepo.loadIssuedBooks(library);
        historyTable.setItems(getHistory());
    }
}
