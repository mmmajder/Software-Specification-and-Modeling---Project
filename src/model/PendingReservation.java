package model;

public class PendingReservation {

    private int id;
    private Member member;
    private Edition edition;

    public PendingReservation() {
    }

    public PendingReservation(int id, Member member, Edition edition) {
        this.id = id;
        this.member = member;
        this.edition = edition;
    }

    public int getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Edition getEdition() {
        return edition;
    }
}
