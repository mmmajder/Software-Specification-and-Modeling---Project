package view.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
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
        libraryRepo.loadPendingReservations(library);
        libraryRepo.loadReservations(library);

        TableColumn colMember = new TableColumn("Member") {
            {
                prefWidthProperty().bind(reservationRequestTable.widthProperty().multiply(0.5));
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
        TableColumn colBookId = new TableColumn("Book ID") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.4));
            }
        };
        approvedReservationsTable.getColumns().add(colBookId);
        TableColumn colDaysLeft = new TableColumn("Days left") {
            {
                prefWidthProperty().bind(approvedReservationsTable.widthProperty().multiply(0.2));
            }
        };
        approvedReservationsTable.getColumns().add(colDaysLeft);

        colMember.setCellValueFactory(new PropertyValueFactory<ReservationRequestTable, LocalDate>("member"));
        colEdition.setCellValueFactory(new PropertyValueFactory<ReservationRequestTable, LocalDate>("edition"));

        colMember2.setCellValueFactory(new PropertyValueFactory<ApprovedReservationTable, String>("member"));
        colBookId.setCellValueFactory(new PropertyValueFactory<ApprovedReservationTable, String>("bookID"));
        colDaysLeft.setCellValueFactory(new PropertyValueFactory<ApprovedReservationTable, Boolean>("daysLeft"));

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
        System.out.println(library.getReservations());
        for (Reservation reservation : library.getReservations()) {
            list.add(new ApprovedReservationTable(reservation.getMember().getName()+" "+reservation.getMember().getSurname(), reservation.getBook().getBookId(), reservation.getDaysLeft()));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        reservationRequestTable.setItems(getRequests());
        approvedReservationsTable.setItems(getApproved());
    }
}
