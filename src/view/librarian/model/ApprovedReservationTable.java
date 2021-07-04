package view.librarian.model;

public class ApprovedReservationTable {
    private int id;
    private String member;
    private String bookID;
    private int daysLeft;

    public ApprovedReservationTable(int id, String member, String bookID, int daysLeft) {
        this.id = id;
        this.member = member;
        this.bookID = bookID;
        this.daysLeft = daysLeft;
    }

    public String getMember() {
        return member;
    }

    public int getId() { return id; }

    public String getBookID() {
        return bookID;
    }

    public int getDaysLeft() {
        return daysLeft;
    }
}
