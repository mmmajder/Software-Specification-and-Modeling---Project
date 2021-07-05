package controller;

import model.*;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.MemberUnableToRentException;

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
        Book book = getBook(pendingReservation);
        notificationController.reservationApproved(pendingReservation);
        removePendingReservation(pendingReservation, member);
        Reservation reservation = new Reservation(getNextReservationId(), member, book, LocalDate.now());
        addReservation(reservation, member);
        library.notifyObservers();
    }

    private Book getBook(PendingReservation pendingReservation){
        Edition edition = pendingReservation.getEdition();

        return library.getAvailableBook(edition);
    }

    private void removePendingReservation(PendingReservation pendingReservation, Member member){
        library.removePendingReservation(pendingReservation);
        libraryRepo.removePendingReservation(pendingReservation);
        member.removePendingReservation();
    }

    private int getNextReservationId(){
        return library.getReservations().stream()
                .map(reservation -> reservation.getId())
                .max(Integer::compare).orElse(0) + 1;
    }

    private void addReservation(Reservation reservation, Member member){
        libraryRepo.addReservation(reservation);
        member.setReservation(reservation);
        library.addReservation(reservation);
    }

    public void declineReservation(int pendingReservationId) {
        PendingReservation pendingReservation = library.getPendingReservation(pendingReservationId);
        Member member = pendingReservation.getMember();
        notificationController.reservationDeclined(pendingReservation);
        removePendingReservation(pendingReservation, member);
        library.notifyObservers();
    }

    public void issueReservation(int reservationId, Account account) throws MemberUnableToRentException {
        Librarian librarian = (Librarian) account.getPerson();
        Reservation reservation = library.getReservation(reservationId);
        Member member = reservation.getMember();
        validateMembersRentingAbilty(member);
        IssuedBook issuedBook = new IssuedBook(member, reservation.getBook(), librarian);
        addIssuedBook(issuedBook, member, librarian);
        removeReservation(reservation, member);
        library.notifyObservers();
    }

    private void validateMembersRentingAbilty(Member member) throws MemberUnableToRentException {
        if (member.getCurrentlyTakenBooks().size() == library.getMaxIssuedBooks(member.getType())){
            throw new MemberUnableToRentException("maxIssuedBooksReached");
        }
    }

    private void removeReservation(Reservation reservation, Member member){
        library.removeReservation(reservation);
        member.removeReservation();
        libraryRepo.removeReservation(reservation);
    }

    private void addIssuedBook(IssuedBook issuedBook, Member member, Librarian librarian){
        member.addTakenBook(issuedBook);
        librarian.addIssuedBook(issuedBook);
        issuedBook.getBook().addIssueHistory(issuedBook);
        library.addIssuedBook(issuedBook);
        libraryRepo.addIssuedBook(issuedBook);
    }

}
