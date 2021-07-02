package controller;

import model.*;
import utils.exceptions.MissingValueException;

import java.time.LocalDate;

public class NotificationController {

    private Library library;

    public NotificationController(Library library){ this.library = library; }

    public void reminderToReturnBook(IssuedBook issuedBook) {
        String bookTitle = issuedBook.getBook().getEdition().getTitle();
        String message = "REMINDER: You should return " + bookTitle + " by " + issuedBook.getReturnDate();
        notify(issuedBook.getMember(), message);
    }

    public void reservationApproved(PendingReservation reservation) {
        String bookTitle = reservation.getEdition().getTitle();
        String message = "Your reservation of " + bookTitle + " is approved.";
        notify(reservation.getMember(), message);
    }

    public void reservationNotApproved(PendingReservation reservation) {
        String bookTitle = reservation.getEdition().getTitle();
        String message = "Your reservation of " + bookTitle + " is not approved. Try again in a few days.";
        notify(reservation.getMember(), message);
    }

    public void reservationExpired(ReservedBook reservation) {
        String bookTitle = reservation.getBook().getEdition().getTitle();
        String message = "Your reservation of " + bookTitle + " is expired.";
        notify(reservation.getMember(), message);
    }

    public void sendFine(IssuedBook issuedBook) {
        String bookTitle = issuedBook.getBook().getEdition().getTitle();
        String message = "You have got fined for not returning the " + bookTitle + " on time.";
        notify(issuedBook.getMember(), message);
    }

    private void notify(Member member, String message) {
        String id = getNewId(member);
        Notification notification = new Notification(id, message, LocalDate.now(), member);
        addNotification(member, notification);
    }

    private String getNewId(Member member){
        return String.valueOf(member.getNotifications().size() + 1);
    }

    private void addNotification(Member member, Notification notification) {
        member.addNotification(notification);
    }
}
