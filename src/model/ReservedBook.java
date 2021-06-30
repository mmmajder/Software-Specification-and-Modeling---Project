package model;

public class ReservedBook {

    private int id;
    private Member member;
    private Book book;

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
}
