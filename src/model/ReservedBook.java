package model;

import java.time.Duration;
import java.time.LocalDate;

public class ReservedBook {

    private int id;
    private Member member;
    private Book book;
    private LocalDate reservedOn;

    public ReservedBook() {
    }

    public ReservedBook(int id, Member member, Book book) {
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
        return (int) Duration.between(LocalDate.now(), reservedOn).toDays();
    }
}
