package view.librarian.model;

public class ApprovedReservationTable {
    private String member;
    private String bookID;
    private int daysLeft;

    public ApprovedReservationTable(String member, String bookID, int daysLeft) {
        this.member = member;
        this.bookID = bookID;
        this.daysLeft = daysLeft;
    }
}