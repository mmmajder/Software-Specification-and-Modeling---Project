package view.member.model;

import java.time.LocalDate;

public class NotificationTable {
    private String notification;
    private LocalDate date;

    public NotificationTable(String notification, LocalDate date) {
        this.notification = notification;
        this.date = date;
    }

    public String getNotification() {
        return notification;
    }

    public LocalDate getDate() {
        return date;
    }
}
