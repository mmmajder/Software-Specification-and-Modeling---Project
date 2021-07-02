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
}
