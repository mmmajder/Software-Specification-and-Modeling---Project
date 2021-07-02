package controller;

import model.*;
import utils.exceptions.MissingValueException;

import java.time.LocalDate;

public class NotificationController {

    private Library library;

    public NotificationController(Library library){ this.library = library; }

    public void reminderToReturnBook(IssuedBook issuedBook) {
        String message = "REMINDER: You should return " + issuedBook.getBook().getEdition().getTitle()
                + " by " + issuedBook.getReturnDate();
        notify(issuedBook.getMember(), message);
    }

    public void reservationApproved(PendingReservation reservation) {
        String message = "Your reservation of " + reservation.getEdition().getTitle() + " is approved.";
        notify(reservation.getMember(), message);
    }

    public void reservationNotApproved(PendingReservation reservation) {
        String message = "Your reservation of " + reservation.getEdition().getTitle() + " is not approved. Try again in a few days.";
        notify(reservation.getMember(), message);
    }

    public void reservationExpired(ReservedBook reservation) {
        String message = "Your reservation of " + reservation.getBook().getEdition().getTitle() + " is expired.";
        notify(reservation.getMember(), message);
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
