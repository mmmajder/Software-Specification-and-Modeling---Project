package view.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.*;
import observer.Observer;
import view.librarian.model.ApprovedReservationTable;
import view.librarian.model.CurrentIssueTable;
import view.librarian.model.MemberTable;
import view.librarian.model.ReservationRequestTable;

import java.io.IOException;

public class ReservationsLibrarianController implements Observer {
    ObservableList<ReservationRequestTable> dataReservationRequestTable;
    ObservableList<ApprovedReservationTable> dataApprovedReservationsTable;
    public TableView<ReservationRequestTable> reservationRequestTable;
    public TableView<ApprovedReservationTable> approvedReservationsTable;

    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    @FXML
    private void initData(Account account) throws IOException {
        this.account = account;
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadPendingReservations(library);
        libraryRepo.loadReservedBooks(library);
        reservationRequestTable.setItems(getRequests());
        approvedReservationsTable.setItems(getApproved());
    }

    private ObservableList<ReservationRequestTable> getRequests() {
        ObservableList<ReservationRequestTable> list = FXCollections.observableArrayList();
        for (PendingReservation reservation : library.getPendingReservations()) {
            list.add(new ReservationRequestTable(reservation.getMember().getName()+" "+reservation.getMember().getSurname(), reservation.getEdition().getTitle()));
        }
        return list;
    }
    private ObservableList<ApprovedReservationTable> getApproved() {
        ObservableList<ApprovedReservationTable> list = FXCollections.observableArrayList();
        for (ReservedBook reservation : library.getReservedBooks()) {
//            list.add(new ApprovedReservationTable(reservation.getMember().getName()+" "+reservation.getMember().getSurname(), reservation.getBook().getBookId(), reservation.getDaysLeft()));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        libraryRepo.loadPendingReservations(library);
        libraryRepo.loadReservedBooks(library);
        reservationRequestTable.setItems(getRequests());
        approvedReservationsTable.setItems(getApproved());
    }
}
