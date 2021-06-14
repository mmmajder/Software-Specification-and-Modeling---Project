package model;

import java.util.List;

import enums.MemberType;

public class Member extends Person {
    private MemberType type;
    private List<Payment> payments;
    private List<IssuedBook> returnedBooks;
    private List<IssuedBook> currentlyTaken;

}
