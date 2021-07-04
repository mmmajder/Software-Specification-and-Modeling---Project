package view.librarian.model;

import java.time.LocalDate;

public class MemberTable {
    private String name;
    private String surname;
    private String JMBG;
    private String phoneNumber;
    private String email;
    private String birthDate;
    private String membershipEndDate;

    public MemberTable(String name, String surname, String JMBG, String phoneNumber, String email, String birthDate, String membershipEndDate) {
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
        this.membershipEndDate = membershipEndDate;
    }

    public MemberTable(String name, String surname, String JMBG, String phoneNumber, String birthDate) {
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "MemberTable{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", JMBG='" + JMBG + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", membershipEndDate=" + membershipEndDate +
                '}';
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

    public String getBirthDate() {
        return birthDate;
    }

    public String getMembershipEndDate() {
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

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setMembershipEndDate(String membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }
}
