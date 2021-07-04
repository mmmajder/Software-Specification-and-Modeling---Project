package view.librarian;

import controller.CRUDController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.*;
import view.librarian.model.CurrentIssueTable;
import view.librarian.model.MemberTable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MemberCRUDController implements Observer {
    public Label removeMemberLbl;
    public Label addMemberLbl;
    public Label prolongLbl;
    public Label addAccountLbl;
    ObservableList<MemberTable> dataMemberTable;
    ObservableList<CurrentIssueTable> dataMemberIssuesTable;

    public TableView<MemberTable> memberTable;
    public TableView<CurrentIssueTable> memberIssuesTable;
    Library library;
    ILibraryRepo libraryRepo;
    MemberTable selected;
    CRUDController crudController;

    public void initData() throws IOException {
        this.library = new Library();
        library.addObserver(this);
        libraryRepo = new LibraryRepo();
        libraryRepo.loadContributors(library);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadIssuedBooks(library);
        crudController = new CRUDController(library);

        TableColumn colName = new TableColumn("Name") {
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.15));
            }
        };
        memberTable.getColumns().add(colName);
        colName.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                MemberTable member = memberTable.getSelectionModel().getSelectedItem();
                member.setName(event.getNewValue().toString());
                try {
                    crudController.editName(member.getName(), member.getJMBG());
                } catch (InvalidNameFormatException e) {
                    createAlert("Name must start with capital letter and contain only alphabetical letters");
                    memberTable.getSelectionModel().getSelectedItem().setName(event.getOldValue().toString());
                }
            }
        });

        TableColumn colSurname = new TableColumn("Surname") {
            {
                prefWidthProperty().bind(memberTable.widthProperty().multiply(0.15));
            }
        };
        memberTable.getColumns().add(colSurname);
        colSurname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                MemberTable member = memberTable.getSelectionModel().getSelectedItem();
                member.setSurname(event.getNewValue().toString());
                try {
                    System.out.println(member.getSurname());
                    System.out.println(member.getJMBG());
                    crudController.editSurname(member.getSurname(), member.getJMBG());
                } catch (InvalidSurnameFormatException e) {
                    createAlert("Surname must start with capital letter and contain only alphabetical letters");
                }
            }
        });

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
        colPhone.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                MemberTable member = memberTable.getSelectionModel().getSelectedItem();
                member.setPhoneNumber(event.getNewValue().toString());
                try {
                    crudController.editPhoneNumber(member.getPhoneNumber(), member.getJMBG());
                } catch (InvalidPhoneNumberFormatException e) {
                    createAlert("Phone number is not written properly");
                }
            }
        });

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
        colSurname.setCellFactory(TextFieldTableCell.forTableColumn());
        colPhone.setCellFactory(TextFieldTableCell.forTableColumn());
        colBirthDate.setCellFactory(TextFieldTableCell.forTableColumn());

        memberTable.setOnMouseClicked(e -> {
            selected = memberTable.getSelectionModel().getSelectedItem();
            loadCurrentIssues();
            memberIssuesTable.setItems(dataMemberIssuesTable);
        });

        removeMemberLbl.setOnMouseClicked(e -> {
            memberTable.getItems().remove(memberTable.getSelectionModel().getSelectedItem());
        });

        addMemberLbl.setOnMouseClicked(e -> {
            Stage window = new Stage();
            window.setTitle("Add member");
            Label nameLbl = new Label("Name ");
            TextField name = new TextField();
            HBox nameHBox = new HBox(nameLbl, name);

            Label surnameLbl = new Label("Surname ");
            TextField surname = new TextField();
            HBox surnameHBox = new HBox(surnameLbl, surname);

            Label jmbgLbl = new Label("JMBG ");
            TextField jmbg = new TextField();
            HBox jmbgHBox = new HBox(jmbgLbl, jmbg);

            Label phoneLbl = new Label("Phone number ");
            TextField phone = new TextField();
            HBox phoneHBox = new HBox(phoneLbl, phone);

            Label birthDateLbl = new Label("Birth date ");
            DatePicker birthDate = new DatePicker();
            HBox birthDateHBox = new HBox(birthDateLbl, birthDate);

            Button confirm = new Button("CONFIRM");

            VBox layout = new VBox(10);
            layout.getChildren().addAll(nameHBox, surnameHBox, jmbgHBox, phoneHBox, birthDateHBox, confirm);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 300, 250);
            window.setScene(scene);
            confirm.setOnAction(event -> {
                System.out.println("Stigao");
                CRUDController crudController = new CRUDController(library);
                String date = birthDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                try {
                    crudController.addMember(name.getText(), surname.getText(), jmbg.getText(), phone.getText(), date);
                    System.out.println("Stigao");
                } catch (InvalidJmbgFormatException invalidJmbgFormatException) {
                    createAlert("Invalid JMBG format");
                } catch (JmbgAlreadyExists jmbgAlreadyExists) {
                    createAlert("JMBG already exists");
                } catch (InvalidNameFormatException invalidNameFormatException) {
                    createAlert("Invalid name format");
                } catch (InvalidSurnameFormatException invalidSurnameFormatException) {
                    createAlert("Invalid surname format");
                } catch (InvalidPhoneNumberFormatException invalidPhoneNumberFormatException) {
                    createAlert("Invalid phone number format");
                } catch (InvalidDateFormatException invalidDateFormatException) {
                    createAlert("Invalid date format");
                }
            });
            window.show();
        });

        prolongLbl.setOnMouseClicked(e -> {
            memberIssuesTable.getSelectionModel().getSelectedItem();
        });
        prolongLbl.setOnMouseClicked(e -> {
            MemberTable member = memberTable.getSelectionModel().getSelectedItem();
            CurrentIssueTable issue = memberIssuesTable.getSelectionModel().getSelectedItem();
            crudController.prolongIssue(member.getJMBG(), issue.getId());
        });

        addAccountLbl.setOnMouseClicked(e -> {
            Stage window = new Stage();
            window.setTitle("Add account");

            Label userLbl = new Label("Username ");
            TextField usename = new TextField();
            HBox userHBox = new HBox(userLbl, usename);

            Label passLbl = new Label("Password ");
            TextField password = new TextField();
            HBox passHBox = new HBox(passLbl, password);

            Label emailLbl = new Label("Email ");
            TextField email = new TextField();
            HBox emailHBox = new HBox(emailLbl, email);

            Button confirm = new Button("CONFIRM");

            VBox layout = new VBox(10);
            layout.getChildren().addAll(userHBox, passHBox, emailHBox, confirm);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 300, 250);
            window.setScene(scene);
            window.showAndWait();

            confirm.setOnAction(event -> {
                CRUDController crudController = new CRUDController(library);
                MemberTable member = memberTable.getSelectionModel().getSelectedItem();
//                crudController.setAccount(member.getJMBG(), usename.getText(), password.getText(), email.getText());
            });

        });
    }

    private void createAlert(String text) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Alert");
        a.setContentText(text);
        System.out.println(text);
        a.show();
    }


    private ObservableList<MemberTable> getMembers() {
        ObservableList<MemberTable> list = FXCollections.observableArrayList();
        for (Member member : library.getMembers()) {
            MemberTable memberTable = new MemberTable(member.getName(), member.getSurname(), member.getJMBG(), member.getPhoneNumber(), member.getBirthDate().toString());
            list.add(memberTable);
//                list.add(new MemberTable(member.getName(), member.getSurname(), member.getJMBG(), member.getPhoneNumber(), member.getAccount().getEmail(), member.getBirthDate().toString(), member.getMembershipExpirationDate().toString()));
            try {
                String expDateTable = member.getMembershipExpirationDate().toString();
                memberTable.setMembershipEndDate(expDateTable);
            } catch (NullPointerException e) {
            }
            try {
                String email = member.getAccount().getEmail();
                memberTable.setMembershipEndDate(email);
            } catch (NullPointerException e) {
            }
        }
        return list;
    }

    private void loadCurrentIssues() {
        dataMemberIssuesTable.clear();
        try {
            for (IssuedBook issuedBook : library.getMembersCurrentlyTakenBooks(selected.getJMBG())) {
                dataMemberIssuesTable.add(new CurrentIssueTable(issuedBook.getBook().getBookId(), issuedBook.getBook().getEdition().getTitle(), issuedBook.isProlongedIssue(), issuedBook.getReturnDate()));
            }
        } catch (NullPointerException e) {
            return;
        }
    }

    @Override
    public void updatePerformed() {
        memberTable.setItems(getMembers());
        loadCurrentIssues();
    }
}
