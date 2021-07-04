package view.admin.model;

public class MemberTableAdmin {
    private String name;
    private String surname;
    private String JMBG;
    private String phoneNumber;
    private String email;
    private String birthDate;
    private String membershipEndDate;
    private String userType;

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMembershipEndDate(String membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }

    public String getSurname() {
        return surname;
    }

    public String getJMBG() {
        return JMBG;
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

    public String getUserType() {
        return userType;
    }

    public MemberTableAdmin(String name, String surname, String JMBG, String phoneNumber, String birthDate, String userType) {
        this.name = name;
        this.surname = surname;
        this.JMBG = JMBG;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.userType = userType;
    }
}
