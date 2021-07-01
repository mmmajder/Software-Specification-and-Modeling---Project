package model;

import java.time.LocalDate;

public class Notification {
    private String id;
    private String message;
    private LocalDate date;
    private Member member;

    public Notification(String id, String message, LocalDate date, Member member) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setNewNotificationId() {
        int id = member.getNotifications().size();
        this.id = String.valueOf(id + 1);
    }

    public Notification reminderToReturnBook(IssuedBook issuedBook) {
        Notification notification = new Notification("1", "REMINDER: You should return " +
                issuedBook.getBook().getEdition().getTitle() + " by " + issuedBook.getReturnDate(),
                LocalDate.now(), issuedBook.getMember());
        notification.setNewNotificationId();
        return notification;
    }

    public Notification reservationApproved(PendingReservation reservation) {
        Notification notification = new Notification("1", "Your reservation of " + reservation.getEdition().getTitle() +
                " is approved.", LocalDate.now(), reservation.getMember());
        notification.setNewNotificationId();
        return notification;
    }

    public Notification reservationNotApproved(PendingReservation reservation) {
        Notification notification = new Notification("1", "Your reservation of " + reservation.getEdition().getTitle() +
                " is not approved. Try again in a few days.", LocalDate.now(), reservation.getMember());
        notification.setNewNotificationId();
        return notification;
    }

    public Notification reservationExpired(PendingReservation reservation) {
        Notification notification = new Notification("1", "Your reservation of " + reservation.getEdition().getTitle() +
                " is expired.", LocalDate.now(), reservation.getMember());
        notification.setNewNotificationId();
        return notification;
    }
}
