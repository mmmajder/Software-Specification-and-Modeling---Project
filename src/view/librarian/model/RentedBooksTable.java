package view.librarian.model;

import java.time.LocalDate;

public class RentedBooksTable {
    private String member;
    private String book;
    private LocalDate issuedDate;
    private LocalDate returnDate;

    public RentedBooksTable(String member, String book, LocalDate issuedDate, LocalDate returnDate) {
        this.member = member;
        this.book = book;
        this.issuedDate = issuedDate;
        this.returnDate = returnDate;
    }
}
