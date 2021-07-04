package controller;

import model.*;
import model.enums.MemberType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class IssuedBookControllerTest {

    @Test
    void calculateReturnDateTrue() {
        Library library = new Library();
        library.setMaxIssueDays(getTestMaxIssueDays());
        IssuedBook issuedBook = getIssuedBook();
        library.addIssuedBook(issuedBook);
        IssuedBookController issuedBookController = new IssuedBookController(library);

        assertEquals(issuedBookController.calculateReturnDate(issuedBook), LocalDate.of(2021, 6, 15));
    }

    @Test
    void calculateReturnDateProlongedTrue() {
        Library library = new Library();
        library.setMaxIssueDays(getTestMaxIssueDays());
        IssuedBook issuedBook = getIssuedBook();
        issuedBook.prolongIssue();
        library.addIssuedBook(issuedBook);
        IssuedBookController issuedBookController = new IssuedBookController(library);

        assertEquals(issuedBookController.calculateReturnDate(issuedBook), LocalDate.of(2021, 6, 20));
    }

    @Test
    void calculateReturnDateFalse() {
        Library library = new Library();
        library.setMaxIssueDays(getTestMaxIssueDays());
        IssuedBook issuedBook = getIssuedBook();
        library.addIssuedBook(issuedBook);
        IssuedBookController issuedBookController = new IssuedBookController(library);

        assertNotEquals(issuedBookController.calculateReturnDate(issuedBook), LocalDate.of(2021, 6, 20));
    }

    @Test
    void calculateReturnDateProlongedFalse() {
        Library library = new Library();
        library.setMaxIssueDays(getTestMaxIssueDays());
        IssuedBook issuedBook = getIssuedBook();
        issuedBook.prolongIssue();
        library.addIssuedBook(issuedBook);
        IssuedBookController issuedBookController = new IssuedBookController(library);

        assertNotEquals(issuedBookController.calculateReturnDate(issuedBook), LocalDate.of(2021, 6, 15));
    }

    private IssuedBook getIssuedBook(){
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setIssueDate(LocalDate.of(2021, 6, 10));
        Member member = new Member();
        member.setType(MemberType.STUDENT);
        issuedBook.setMember(member);

        return issuedBook;
    }

    private HashMap<MemberType, Integer> getTestMaxIssueDays(){
        HashMap <MemberType, Integer> maxNums = new HashMap<>();
        maxNums.put(MemberType.REGULAR, 2);
        maxNums.put(MemberType.STUDENT, 5);
        maxNums.put(MemberType.PRESCHOOLER, 5);
        maxNums.put(MemberType.PUPIL, 2);
        maxNums.put(MemberType.RETIRED, 2);

        return maxNums;
    }
}