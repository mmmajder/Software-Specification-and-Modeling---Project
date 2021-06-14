package model;

import java.time.LocalDate;

public class IssuedBook {
    LocalDate issueDate;
    LocalDate returnDate;
    boolean prolongedIssue;

    public IssuedBook(LocalDate issueDate){
        this.issueDate = issueDate;
        this.returnDate = null;
        this.prolongedIssue = false;
    }
}
