package view.librarian.model;

import java.time.LocalDate;

public class MemberTable {
    private String name;
    private String surname;
    private String JMBG;
    private String phoneNumber;
    private String email;
    private LocalDate birthDate;
    private LocalDate membershipEndDate;

    public MemberTable(String name, String surname, String JMBG, String phoneNumber, String email, LocalDate birthDate, LocalDate membershipEndDate) {
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
        this.membershipEndDate = membershipEndDate;
    }

    public String getJMBG() {
        return JMBG;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setMembershipEndDate(LocalDate membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }
}
