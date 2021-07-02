package view.librarian.model;

public class ReservationRequestTable {
    private String member;
    private String edition;

    public ReservationRequestTable(String member, String edition) {
        this.member = member;
        this.edition = edition;
    }

    public String getMember() {
        return member;
    }

    public String getEdition() {
        return edition;
    }
}
