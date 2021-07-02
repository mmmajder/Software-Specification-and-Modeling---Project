package view.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.*;
import observer.Observer;
import view.librarian.model.CurrentIssueTable;
import view.librarian.model.MemberTable;

import java.io.IOException;
import java.time.LocalDate;

public class MemberCRUDController implements Observer {
    public Label removeMemberLbl;
    public Label addMemberLbl;
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
        libraryRepo.loadIssuedBooks(library);
        colName.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("surname"));
        colJMBG.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("JMBG"));
        colPhone.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("email"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<MemberTable, LocalDate>("birthDate"));
        colMembershipEndDate.setCellValueFactory(new PropertyValueFactory<MemberTable, LocalDate>("membershipEndDate"));
        dataMemberTable = getMembers();
        memberTable.setItems(dataMemberTable);
        dataMemberIssuesTable = FXCollections.observableArrayList();

        memberTable.setEditable(true);
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colJMBG.setCellFactory(TextFieldTableCell.forTableColumn());
        colPhone.setCellFactory(TextFieldTableCell.forTableColumn());
        colEmail.setCellFactory(TextFieldTableCell.forTableColumn());
//        colBirthDate.setCellFactory(TextFieldTableCell.forTableColumn());
//        colMembershipEndDate.setCellFactory(TextFieldTableCell.forTableColumn());

        memberTable.setOnMouseClicked(e -> {
            System.out.println(memberTable.getSelectionModel().getSelectedItems());
            loadCurrentIssues();
            System.out.println(dataMemberIssuesTable);
            memberIssuesTable.setItems(dataMemberIssuesTable);
        });

        removeMemberLbl.setOnMouseClicked(e -> {
            MemberTable selectedItem = memberTable.getSelectionModel().getSelectedItem();
            memberTable.getItems().remove(selectedItem);
        });

        addMemberLbl.setOnMouseClicked(e -> {
            dataMemberTable.add(new MemberTable("Name", "Surname", "JMBG", "Phone", "Email", LocalDate.of(2001, 1, 1), LocalDate.of(2001, 1, 1)));
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
        for (MemberTable row : memberTable.getSelectionModel().getSelectedItems()) {
            for (int i = 1; i <= 1; i++) {
                try {
                    System.out.println(library.getMembersCurrentlyTakenBooks(row.getJMBG()));
                    for (IssuedBook issuedBook : library.getMembersCurrentlyTakenBooks(row.getJMBG())) {
                        System.out.println("Stigao: " + issuedBook.getBook().getBookId());
                        dataMemberIssuesTable.add(new CurrentIssueTable(issuedBook.getBook().getBookId(), issuedBook.getBook().getEdition().getTitle(), issuedBook.isProlongedIssue(), issuedBook.getReturnDate()));
                    }
                } catch (NullPointerException e) {
                    return;
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

    public void editNameChanged(TableColumn.CellEditEvent<MemberTable, String> memberTableStringCellEditEvent) {
        MemberTable member = memberTable.getSelectionModel().getSelectedItem();
        member.setName(memberTableStringCellEditEvent.getNewValue());
//        Member member1 = (Member) library.getPerson(member.getJMBG());
//        member1.setName(member.getName());
    }

    public void editSurnameChanged(TableColumn.CellEditEvent<MemberTable, String> memberTableStringCellEditEvent) {
        MemberTable member = memberTable.getSelectionModel().getSelectedItem();
        member.setSurname(memberTableStringCellEditEvent.getNewValue());
//        Member member1 = (Member) library.getPerson(member.getJMBG());
//        member1.setSurname(member.getSurname());
    }

    public void editPhoneNumberChanged(TableColumn.CellEditEvent<MemberTable, String> memberTableStringCellEditEvent) {
        MemberTable member = memberTable.getSelectionModel().getSelectedItem();
        member.setPhoneNumber(memberTableStringCellEditEvent.getNewValue());
//        Member member1 = (Member) library.getPerson(member.getJMBG());
//        member1.setPhoneNumber(member.getPhoneNumber());
    }

    public void editEmailChanged(TableColumn.CellEditEvent<MemberTable, String> memberTableStringCellEditEvent) {
        MemberTable member = memberTable.getSelectionModel().getSelectedItem();
        member.setEmail(memberTableStringCellEditEvent.getNewValue());
//        Member member1 = (Member) library.getPerson(member.getJMBG());
//        member1.getAccount().setEmail(member.getEmail());
    }

}
