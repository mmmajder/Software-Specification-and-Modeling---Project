package controller;

import model.*;

import java.time.LocalDate;

public class NotificationController {

    private Library library;

    public NotificationController(Library library){ this.library = library; }

    public void reminderToReturnBook(IssuedBook issuedBook) {
        String bookTitle = issuedBook.getBook().getEdition().getTitle();
        String message = "REMINDER: You should return " + bookTitle + " by " + issuedBook.getReturnDate();
        notify(issuedBook.getMember().getAccount(), message);
    }

    public void reservationApproved(PendingReservation reservation) {
        String bookTitle = reservation.getEdition().getTitle();
        String message = "Your reservation of " + bookTitle + " is approved.";
        notify(reservation.getMember().getAccount(), message);
    }

    public void reservationNotApproved(PendingReservation reservation) {
        String bookTitle = reservation.getEdition().getTitle();
        String message = "Your reservation of " + bookTitle + " is not approved. Try again in a few days.";
        notify(reservation.getMember().getAccount(), message);
    }

    public void reservationExpired(Reservation reservation) {
        String bookTitle = reservation.getBook().getEdition().getTitle();
        String message = "Your reservation of " + bookTitle + " is expired.";
        notify(reservation.getMember().getAccount(), message);
    }

    public void sendFine(IssuedBook issuedBook) {
        String bookTitle = issuedBook.getBook().getEdition().getTitle();
        String message = "You have got fined for not returning the " + bookTitle + " on time.";
        notify(issuedBook.getMember().getAccount(), message);
    }

    private void notify(Account account, String message) {
        String id = getNewId(account);
        Notification notification = new Notification(id, message, LocalDate.now(), account);
        addNotification(account, notification);
    }

    private String getNewId(Account account){
        return String.valueOf(account.getNotifications().size() + 1);
    }

    private void addNotification(Account account, Notification notification) {
        account.addNotification(notification);
    }
}
