package model;

import java.time.LocalDate;

public class Notification {
    private String id;
    private String message;
    private LocalDate date;
    private Account account;

    public Notification(String id, String message, LocalDate date, Account account) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.account = account;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
}
