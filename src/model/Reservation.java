package model;

import java.time.Duration;
import java.time.LocalDate;

public class Reservation {

    private int id;
    private Member member;
    private Book book;
    private LocalDate reservedOn;

    public Reservation() {
    }

    public Reservation(int id, Member member, Book book) {
        this.id = id;
        this.member = member;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getReservedOn() { return reservedOn; }

    public int getDaysLeft(){
        return 3 - (int) Duration.between(reservedOn, LocalDate.now()).toDays();
    }
}
