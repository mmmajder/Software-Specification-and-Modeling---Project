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

    public void approveReservation(String jmbg, String editionId) {

        Member member = (Member) library.getPerson(jmbg);
        Edition edition = library.getEdition(editionId);
        Book book = library.getAvailableBook(edition);
        PendingReservation pendingReservation = member.getPendingReservation();
        notificationController.reservationApproved(pendingReservation);

        library.removePendingReservation(pendingReservation);
        libraryRepo.removePendingReservation(pendingReservation);
        member.removePendingReservation();

        int nextId = library.getReservations().size() + 1;
        Reservation reservation = new Reservation(nextId, member, book, LocalDate.now());
        libraryRepo.addReservation(reservation);
        member.setReservation(reservation);
        library.addReservation(reservation);

        library.notifyObservers();

    }

    public void declineReservation(String jmbg) {

        Member member = (Member) library.getPerson(jmbg);
        PendingReservation pendingReservation = member.getPendingReservation();
        notificationController.reservationDeclined(pendingReservation);

        library.removePendingReservation(pendingReservation);
        libraryRepo.removePendingReservation(pendingReservation);
        member.removePendingReservation();

        library.notifyObservers();
    }

    public void issueReservation(String jmbg, Account account) {

        Librarian librarian = (Librarian) account.getPerson();
        Member member = (Member) library.getPerson(jmbg);
        Book book = member.getReservedBook();
        Reservation reservation = member.getReservation();

        library.removeReservation(reservation);
        member.removeReservation();
        libraryRepo.removeReservation(reservation);

        IssuedBook issuedBook = new IssuedBook(member, book, librarian);
        member.addTakenBook(issuedBook);
        librarian.addIssuedBook(issuedBook);
        book.addIssueHistory(issuedBook);
        library.addIssuedBook(issuedBook);
        libraryRepo.addReservation(reservation);

        library.notifyObservers();
    }

}
