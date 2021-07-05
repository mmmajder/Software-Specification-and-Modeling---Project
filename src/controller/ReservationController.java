package controller;

import model.*;
import repository.ILibraryRepo;
import repository.LibraryRepo;

import java.time.LocalDate;

public class ReservationController {

    private Library library;
    private ILibraryRepo libraryRepo;
    private NotificationController notificationController;

    public ReservationController(Library library) {
        this.notificationController = new NotificationController(library);
        this.library = library;
        this.libraryRepo = new LibraryRepo();
    }

    public void approveReservation(int pendingReservationId) {

        PendingReservation pendingReservation = library.getPendingReservation(pendingReservationId);
        Member member = pendingReservation.getMember();
        Edition edition = pendingReservation.getEdition();
        Book book = library.getAvailableBook(edition);
        notificationController.reservationApproved(pendingReservation);

        library.removePendingReservation(pendingReservation);
        libraryRepo.removePendingReservation(pendingReservation);
        libraryRepo.deleteMembersPendingReservation(member);
        member.removePendingReservation();

        int nextId = library.getReservations().size() + 1;
        Reservation reservation = new Reservation(nextId, member, book, LocalDate.now());
        libraryRepo.addMembersReservation(member, reservation);
        libraryRepo.addReservation(reservation);
        member.setReservation(reservation);
        library.addReservation(reservation);

        library.notifyObservers();

    }

    public void declineReservation(int pendingReservationId) {

        PendingReservation pendingReservation = library.getPendingReservation(pendingReservationId);
        Member member = pendingReservation.getMember();
        notificationController.reservationDeclined(pendingReservation);

        library.removePendingReservation(pendingReservation);
        libraryRepo.removePendingReservation(pendingReservation);
        libraryRepo.deleteMembersPendingReservation(member);
        member.removePendingReservation();

        library.notifyObservers();
    }

    public void issueReservation(int reservationId, Account account) {

        Librarian librarian = (Librarian) account.getPerson();
        Reservation reservation = library.getReservation(reservationId);
        Member member = reservation.getMember();
        Book book = reservation.getBook();

        library.removeReservation(reservation);
        member.removeReservation();
        libraryRepo.removeReservation(reservation);
        libraryRepo.deleteMembersReservation(member);

        IssuedBook issuedBook = new IssuedBook(member, book, librarian);
        member.addTakenBook(issuedBook);
        librarian.addIssuedBook(issuedBook);
        book.addIssueHistory(issuedBook);
        library.addIssuedBook(issuedBook);
        libraryRepo.addIssuedBook(issuedBook);

        library.notifyObservers();
    }

}
