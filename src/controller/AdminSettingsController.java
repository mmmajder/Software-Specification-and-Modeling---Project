package controller;

import model.Library;
import model.enums.MemberType;

import java.util.List;

public class AdminSettingsController {

    private Library library;

    public AdminSettingsController(Library library) { this.library = library; }

    public void updateMaxIssueDays(List<Integer> maxIssueDays){
        setNewMaxIssueDays(MemberType.REGULAR, maxIssueDays.get(0));
        setNewMaxIssueDays(MemberType.STUDENT, maxIssueDays.get(1));
        setNewMaxIssueDays(MemberType.PRESCHOOLER, maxIssueDays.get(2));
        setNewMaxIssueDays(MemberType.PUPIL, maxIssueDays.get(3));
        setNewMaxIssueDays(MemberType.RETIRED, maxIssueDays.get(4));
    }

    private void setNewMaxIssueDays(MemberType type, Integer newMaxIssueDays){
        if (newMaxIssueDays != null){
            library.setMaxIssueDays(type, newMaxIssueDays);
        }
    }

    public void updateMaxIssuedBooks(List<Integer> maxIssuedBooks){
        setNewMaxIssuedBooks(MemberType.REGULAR, maxIssuedBooks.get(0));
        setNewMaxIssuedBooks(MemberType.STUDENT, maxIssuedBooks.get(1));
        setNewMaxIssuedBooks(MemberType.PRESCHOOLER, maxIssuedBooks.get(2));
        setNewMaxIssuedBooks(MemberType.PUPIL, maxIssuedBooks.get(3));
        setNewMaxIssuedBooks(MemberType.RETIRED, maxIssuedBooks.get(4));
    }

    private void setNewMaxIssuedBooks(MemberType type, Integer newMaxIssuedBooks){
        if (newMaxIssuedBooks != null){
            library.setMaxIssueDays(type, newMaxIssuedBooks);
        }
    }

    public void updatePriceCatalogue6(){

    }

    public void updatePriceCatalogue12(){

    }
}
