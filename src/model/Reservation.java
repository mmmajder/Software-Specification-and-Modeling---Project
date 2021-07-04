package model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class Reservation {

    private int id;
    private Member member;
    private Book book;
    private LocalDate reservedOn;

    public Reservation() {
    }

    public Reservation(int id, Member member, Book book, LocalDate reservedOn) {
        this.id = id;
        this.member = member;
        this.book = book;
        this.reservedOn = reservedOn;
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

    public LocalDate getReservedOn() {
        return reservedOn;
    }

    public int getDaysLeft() {
        return (int) (3 - ChronoUnit.DAYS.between(reservedOn, LocalDate.now()));
    }

    public String getMemberFullName() {
        return this.getMember().getFullName();
    }
}
