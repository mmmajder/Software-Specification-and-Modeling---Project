package view.librarian;

import com.sun.xml.internal.bind.v2.runtime.property.PropertyFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import observer.Observer;
import view.librarian.model.BookEditionTable;
import view.librarian.model.BookSampleTable;
import view.librarian.model.CurrentIssueTable;
import view.librarian.model.MemberTable;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MemberCRUDController implements Observer {
    ObservableList<MemberTable> dataMemberTable;
    ObservableList<CurrentIssueTable> dataMemberIssuesTable;
    public TableView<MemberTable> memberTable;
    public TableColumn<MemberTable, String> colName;
    public TableColumn<MemberTable, String> colSurname;
    public TableColumn<MemberTable, String> colJMBG;
    public TableColumn<MemberTable, String> colPhone;
    public TableColumn<MemberTable, String> colEmail;
    public TableColumn<MemberTable, LocalDate> colBirthDate;
    public TableColumn<MemberTable, LocalDate> colMembershipEndDate;
    public TableView<CurrentIssueTable> memberIssuesTable;
    Library library;
    ILibraryRepo libraryRepo;

    public void initData() throws IOException  {
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        colName.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("surname"));
        colJMBG.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("JMBG"));
        colPhone.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("email"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<MemberTable, LocalDate>("birthDate"));
        colMembershipEndDate.setCellValueFactory(new PropertyValueFactory<MemberTable, LocalDate>("membershipEndDate"));
        memberTable.setItems(getMembers());

//        MemberTable member = new MemberTable("Milan", "Ajder", "member.getJMBG()", "member.getPhoneNumber()", "member.getAccount().getEmail()", "member.getBirthDate().toString()", "member.getMembershipExpirationDate().toString()");
//        memberTable.getItems().add(member);

//        memberTable.setItems(getMembers());

        memberTable.setOnMouseClicked(e -> {
            System.out.println(memberTable.getSelectionModel().getSelectedItems());
            loadCurrentIssues();
            memberIssuesTable.setItems(dataMemberIssuesTable);
        });

    }

    private ObservableList<MemberTable> getMembers() {
        ObservableList<MemberTable> list = FXCollections.observableArrayList();
        for (Member member : library.getMembers()) {
            list.add(new MemberTable(member.getName(), member.getSurname(), member.getJMBG(), member.getPhoneNumber(), member.getAccount().getEmail(), member.getBirthDate(), member.getMembershipExpirationDate()));

        }
        return list;
    }

    private void loadCurrentIssues() {
        dataMemberIssuesTable = FXCollections.observableArrayList();
        for (MemberTable row : memberTable.getSelectionModel().getSelectedItems()) {
            for (int i = 1; i <= 1; i++) {
                for (IssuedBook issuedBook : library.getMemberActiveIssues(row.getJMBG())) {
                    dataMemberIssuesTable.add(new CurrentIssueTable(issuedBook.getBook().getBookId(), issuedBook.getBook().getEdition().getTitle(), issuedBook.isProlongedIssue(), issuedBook.getReturnDate()));
                }
            }
        }
    }

    @Override
    public void updatePerformed() {
        try {
            initData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
