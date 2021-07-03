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
    public TableView<CurrentIssueTable> memberIssuesTable;
    Library library;
    ILibraryRepo libraryRepo;

    public void initData() throws IOException  {
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadContributors(library);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadIssuedBooks(library);

        TableColumn colName = new TableColumn("Name"){
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.15));
            }
        };
        memberTable.getColumns().add(colName);

        TableColumn colSurname = new TableColumn("Surname") {
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.15));
            }
        };
        memberTable.getColumns().add(colSurname);

        TableColumn colJMBG = new TableColumn("JMBG") {
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.1));
            }
        };
        memberTable.getColumns().add(colJMBG);

        TableColumn colPhone = new TableColumn("Phone number") {
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.15));
            }
        };
        memberTable.getColumns().add(colPhone);

        TableColumn colEmail = new TableColumn("Email") {
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.15));
            }
        };
        memberTable.getColumns().add(colEmail);

        TableColumn colBirthDate = new TableColumn("Birth date") {
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.1));
            }
        };
        memberTable.getColumns().add(colBirthDate);

        TableColumn colMembershipEndDate = new TableColumn("Membership end date") {
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.2));
            }
        };
        memberTable.getColumns().add(colMembershipEndDate);


        TableColumn colID = new TableColumn("ID") {
            {
                prefWidthProperty().bind(memberIssuesTable.widthProperty().multiply(0.2));
            }
        };
        memberIssuesTable.getColumns().add(colID);

        TableColumn colTitle = new TableColumn("Title") {
            {
                prefWidthProperty().bind(memberIssuesTable.widthProperty().multiply(0.3));
            }
        };
        memberIssuesTable.getColumns().add(colTitle);

        TableColumn colProlonged = new TableColumn("Prolonged") {
            {
                prefWidthProperty().bind(memberIssuesTable.widthProperty().multiply(0.2));
            }
        };
        memberIssuesTable.getColumns().add(colProlonged);

        TableColumn colReturnDate = new TableColumn("Return date") {
            {
                prefWidthProperty().bind(memberIssuesTable.widthProperty().multiply(0.3));
            }
        };
        memberIssuesTable.getColumns().add(colReturnDate);


        colName.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("name"));
        colSurname.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("surname"));
        colJMBG.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("JMBG"));
        colPhone.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("phoneNumber"));
        colEmail.setCellValueFactory(new PropertyValueFactory<MemberTable, String>("email"));
        colBirthDate.setCellValueFactory(new PropertyValueFactory<MemberTable, LocalDate>("birthDate"));
        colMembershipEndDate.setCellValueFactory(new PropertyValueFactory<MemberTable, LocalDate>("membershipEndDate"));

        colID.setCellValueFactory(new PropertyValueFactory<CurrentIssueTable, String>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<CurrentIssueTable, String>("title"));
        colProlonged.setCellValueFactory(new PropertyValueFactory<CurrentIssueTable, Boolean>("prolonged"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<CurrentIssueTable, LocalDate>("returnDate"));

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
                    for (IssuedBook issuedBook : library.getMembersCurrentlyTakenBooks(row.getJMBG())) {
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
