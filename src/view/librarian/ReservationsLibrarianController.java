package view.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import model.*;
import observer.Observer;
import view.librarian.model.ApprovedReservationTable;
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

    public void initData() throws IOException {
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadPendingReservations(library);
        libraryRepo.loadReservations(library);
        reservationRequestTable.setItems(getRequests());
        approvedReservationsTable.setItems(getApproved());
    }

    private ObservableList<ReservationRequestTable> getRequests() {
        ObservableList<ReservationRequestTable> list = FXCollections.observableArrayList();
        System.out.println(library.getPendingReservations());
        for (PendingReservation reservation : library.getPendingReservations()) {
            list.add(new ReservationRequestTable(reservation.getMember().getName()+" "+reservation.getMember().getSurname(), reservation.getEdition().getTitle()));
        }
        return list;
    }
    private ObservableList<ApprovedReservationTable> getApproved() {
        ObservableList<ApprovedReservationTable> list = FXCollections.observableArrayList();
        for (Reservation reservation : library.getReservations()) {
//            list.add(new ApprovedReservationTable(reservation.getMember().getName()+" "+reservation.getMember().getSurname(), reservation.getBook().getBookId(), reservation.getDaysLeft()));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        libraryRepo.loadPendingReservations(library);
        libraryRepo.loadReservations(library);
        reservationRequestTable.setItems(getRequests());
        approvedReservationsTable.setItems(getApproved());
    }
}
