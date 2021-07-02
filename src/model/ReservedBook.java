package model;

import com.sun.swing.internal.plaf.synth.resources.synth_sv;

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
        return 3 - (int) Duration.between(reservedOn, LocalDate.now()).toDays();
    }
}
