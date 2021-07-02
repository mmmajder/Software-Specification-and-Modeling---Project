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
}
