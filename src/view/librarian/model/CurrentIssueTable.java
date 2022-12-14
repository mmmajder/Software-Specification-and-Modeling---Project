package view.librarian.model;

import java.time.LocalDate;

public class CurrentIssueTable {
    private String id;
    private String title;
    private boolean prolonged;
    private LocalDate returnDate;

    public CurrentIssueTable(String id, String title, boolean prolonged, LocalDate returnDate) {
        this.id = id;
        this.title = title;
        this.prolonged = prolonged;
        this.returnDate = returnDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isProlonged() {
        return prolonged;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
