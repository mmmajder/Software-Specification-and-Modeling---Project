package view.librarian;

import controller.ReservationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Account;
import model.Library;
import model.PendingReservation;
import model.Reservation;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import view.librarian.model.ApprovedReservationTable;
import view.librarian.model.ReservationRequestTable;

import java.io.IOException;

public class ReservationsLibrarianController implements Observer {
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

        TableColumn<ReservationRequestTable, Integer> colId = new TableColumn<ReservationRequestTable, Integer>("id") {
            {
                prefWidthProperty().bind(reservationRequestTable.widthProperty().multiply(0.2));
            }
        };
        reservationRequestTable.getColumns().add(colId);
        TableColumn<ReservationRequestTable, String> colMember = new TableColumn<ReservationRequestTable, String>("Member") {
            {
                prefWidthProperty().bind(reservationRequestTable.widthProperty().multiply(0.3));
            }
        };
        reservationRequestTable.getColumns().add(colMember);
        TableColumn<ReservationRequestTable, String> colEdition = new TableColumn<ReservationRequestTable, String>("Edition") {
            {
                prefWidthProperty().bind(reservationRequestTable.widthProperty().multiply(0.5));
            }
        };

        reservationRequestTable.getColumns().add(colEdition);

        TableColumn<ApprovedReservationTable, String> colMember2 = new TableColumn<ApprovedReservationTable, String>("Member") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.4));
            }
        };
        approvedReservationsTable.getColumns().add(colMember2);
        TableColumn<ApprovedReservationTable, Integer> colId2 = new TableColumn<ApprovedReservationTable, Integer>("id") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.2));
            }
        };
        approvedReservationsTable.getColumns().add(colId2);
        TableColumn<ApprovedReservationTable, Integer> colBookId = new TableColumn<ApprovedReservationTable, Integer>("Book ID") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.2));
            }
        };
        approvedReservationsTable.getColumns().add(colBookId);
        TableColumn<ApprovedReservationTable, Integer> colDaysLeft = new TableColumn<ApprovedReservationTable, Integer>("Days left") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.2));
            }
        };
        approvedReservationsTable.getColumns().add(colDaysLeft);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMember.setCellValueFactory(new PropertyValueFactory<>("member"));
        colEdition.setCellValueFactory(new PropertyValueFactory<>("edition"));

        colId2.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMember2.setCellValueFactory(new PropertyValueFactory<>("member"));
        colBookId.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colDaysLeft.setCellValueFactory(new PropertyValueFactory<>("daysLeft"));

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

    public void issue() {
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
