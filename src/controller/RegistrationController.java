package controller;

import model.Account;
import model.Librarian;
import model.Member;
import model.Person;
import model.enums.MemberType;
import utils.StringUtils;
import utils.exceptions.MissingValueException;

import java.time.LocalDate;
import java.util.ArrayList;

public class RegistrationController {

    private ArrayList<Person> persons;

    public RegistrationController(ArrayList<Person> persons){
        this.persons = persons;
    }

    public void registerMember(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account, MemberType type) throws MissingValueException {
        validateInputValuesMember(name, surname, JMBG, phoneNumber, birthDate, type);
        Member m = new Member(name, surname, JMBG, phoneNumber, birthDate, type);
        if (account != null){ m.setAccount(account); }
        persons.add(m);
    }

    public void registerLibrarian(String name, String surname, String JMBG, String phoneNumber, LocalDate birthDate, Account account, MemberType type) throws MissingValueException {
        validateInputValues(name, surname, JMBG, phoneNumber, birthDate);
        Librarian l = new Librarian(name, surname, JMBG, phoneNumber, birthDate, account);
        persons.add(l);
    }

    private void validateInputValues(String name, String surname, String jmbg, String phoneNumber, LocalDate birthDate) throws MissingValueException {
        if (StringUtils.isNullOrEmpty(name)) { throw new MissingValueException("name"); }
        if (StringUtils.isNullOrEmpty(surname)) { throw new MissingValueException("surname"); }
        if (StringUtils.isNullOrEmpty(jmbg)) { throw new MissingValueException("name"); }
        if (StringUtils.isNullOrEmpty(phoneNumber)) { throw new MissingValueException("surname"); }
        if (birthDate == null) { throw new MissingValueException("birthDate"); }
    }

    private void validateInputValuesMember(String name, String surname, String jmbg, String phoneNumber, LocalDate birthDate, MemberType memberType) throws MissingValueException {
        validateInputValues(name, surname, jmbg, phoneNumber, birthDate);
        if (memberType == null) { throw new MissingValueException("memberType"); }
    }
}
