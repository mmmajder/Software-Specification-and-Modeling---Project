package view.librarian;

import controller.ReservationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.*;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.MemberUnableToRentException;
import view.librarian.model.ApprovedReservationTable;
import view.librarian.model.CurrentIssueTable;
import view.librarian.model.MemberTable;
import view.librarian.model.ReservationRequestTable;

import java.io.IOException;
import java.time.LocalDate;

public class ReservationsLibrarianController implements Observer {
    ObservableList<ReservationRequestTable> dataReservationRequestTable;
    ObservableList<ApprovedReservationTable> dataApprovedReservationsTable;
    public TableView<ReservationRequestTable> reservationRequestTable;
    public TableView<ApprovedReservationTable> approvedReservationsTable;
    private ReservationController reservationController;

    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    public void initData(Account account) throws IOException {
        this.library = new Library();
        this.account = account;
        libraryRepo = new LibraryRepo();
        library.addObserver(this);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadPendingReservations(library);
        libraryRepo.loadReservations(library);
        reservationController = new ReservationController(library);

        TableColumn colId = new TableColumn("id") {
            {
                prefWidthProperty().bind(reservationRequestTable.widthProperty().multiply(0.2));
            }
        };
        TableColumn colMember = new TableColumn("Member") {
            {
                prefWidthProperty().bind(reservationRequestTable.widthProperty().multiply(0.3));
            }
        };
        reservationRequestTable.getColumns().add(colMember);
        TableColumn colEdition = new TableColumn("Edition") {
            {
                prefWidthProperty().bind(reservationRequestTable.widthProperty().multiply(0.5));
            }
        };
        reservationRequestTable.getColumns().add(colEdition);

        TableColumn colMember2 = new TableColumn("Member") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.4));
            }
        };
        approvedReservationsTable.getColumns().add(colMember2);
        TableColumn colId2 = new TableColumn("id") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.2));
            }
        };
        approvedReservationsTable.getColumns().add(colId2);
        TableColumn colBookId = new TableColumn("Book ID") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.2));
            }
        };
        approvedReservationsTable.getColumns().add(colBookId);
        TableColumn colDaysLeft = new TableColumn("Days left") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.2));
            }
        };
        approvedReservationsTable.getColumns().add(colDaysLeft);

        colId.setCellValueFactory(new PropertyValueFactory<ReservationRequestTable, Integer>("member"));
        colMember.setCellValueFactory(new PropertyValueFactory<ReservationRequestTable, String>("member"));
        colEdition.setCellValueFactory(new PropertyValueFactory<ReservationRequestTable, LocalDate>("edition"));

        colId2.setCellValueFactory(new PropertyValueFactory<ApprovedReservationTable, Integer>("id"));
        colMember2.setCellValueFactory(new PropertyValueFactory<ApprovedReservationTable, String>("member"));
        colBookId.setCellValueFactory(new PropertyValueFactory<ApprovedReservationTable, String>("bookID"));
        colDaysLeft.setCellValueFactory(new PropertyValueFactory<ApprovedReservationTable, Boolean>("daysLeft"));

        reservationRequestTable.setItems(getRequests());
        approvedReservationsTable.setItems(getApproved());
    }

    private ObservableList<ReservationRequestTable> getRequests() {
        ObservableList<ReservationRequestTable> list = FXCollections.observableArrayList();
        for (PendingReservation reservation : library.getPendingReservations()) {
            list.add(new ReservationRequestTable(reservation.getId(), reservation.getMember().getFullName(), reservation.getEdition().getTitle()));
        }
        return list;
    }

    private ObservableList<ApprovedReservationTable> getApproved() {
        ObservableList<ApprovedReservationTable> list = FXCollections.observableArrayList();
        for (Reservation reservation : library.getReservations()) {
            list.add(new ApprovedReservationTable(reservation.getId(), reservation.getMemberFullName(), reservation.getBook().getBookId(), reservation.getDaysLeft()));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        reservationRequestTable.setItems(getRequests());
        approvedReservationsTable.setItems(getApproved());
    }

    public void issue() throws MemberUnableToRentException {
        ApprovedReservationTable reservation = approvedReservationsTable.getSelectionModel().getSelectedItem();
        reservationController.issueReservation(reservation.getId(), account);
    }

    public void decline() {
        ReservationRequestTable reservation = reservationRequestTable.getSelectionModel().getSelectedItem();
        reservationController.declineReservation(reservation.getId());
    }

    public void approve() {
        ReservationRequestTable reservation = reservationRequestTable.getSelectionModel().getSelectedItem();
        reservationController.approveReservation(reservation.getId());
    }
}
