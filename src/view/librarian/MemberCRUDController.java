package view.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.*;
import view.librarian.model.BookEditionTable;
import view.librarian.model.BookSampleTable;
import view.librarian.model.CurrentIssueTable;
import view.librarian.model.MemberTable;

import java.io.IOException;

public class MemberCRUDController {
    ObservableList<MemberTable> dataMemberTable;
    ObservableList<CurrentIssueTable> dataMemberIssuesTable;
    public TableView<MemberTable> memberTable;
    public TableView<CurrentIssueTable> memberIssuesTable;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    @FXML
    private void initData(Account account) throws IOException {
        this.account = account;
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        memberTable.setItems(getMembers());

        memberTable.setOnMouseClicked(e -> {
            loadCurrentIssues();
        });

    }

    private ObservableList<MemberTable> getMembers() {
        ObservableList<MemberTable> list = FXCollections.observableArrayList();
//        for (Member member : library.getMembers()) {
//            list.add(new MemberTable(member.getName(), member.getSurname(), member.getJMBG(), member.getPhoneNumber(), member.getAccount().getEmail(), member.getBirthDate(), member.getMembershipExpirationDate()));
//        }
        return list;
    }

    private void loadCurrentIssues() {
        dataMemberIssuesTable.clear();
        for (MemberTable row : memberTable.getSelectionModel().getSelectedItems()) {
            for (int i = 1; i <= 1; i++) {
//                for (IssuedBook issuedBook : library.getActiveIssues(row.getJMBG())) {
//                    dataMemberIssuesTable.add(new CurrentIssueTable(issuedBook.getBook().getBookId(), issuedBook.getBook().getEdition().getTitle(), issuedBook.isProlongedIssue(), issuedBook.getReturnDate());
//                }
            }
        }
    }


}
