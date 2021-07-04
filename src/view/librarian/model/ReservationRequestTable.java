package view.librarian.model;

public class ReservationRequestTable {
    private int id;
    private String member;
    private String edition;

    public ReservationRequestTable(int id, String member, String edition) {
        this.id = id;
        this.member = member;
        this.edition = edition;
    }

    public int getId() {
        return id;
    }

    public String getMember() {
        return member;
    }

    public String getEdition() {
        return edition;
    }
}
