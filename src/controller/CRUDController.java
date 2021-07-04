package controller;

import model.*;
import model.enums.MemberType;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CRUDController {

    private Library library;
    private ILibraryRepo libraryRepo;

    public CRUDController(Library library) {
        this.library = library;
        this.libraryRepo = new LibraryRepo();
    }

    public void editName(String name, String jmbg) throws InvalidNameFormatException {

        nameValid(name);
        Person person = library.getPerson(jmbg);
        person.setName(name);
        libraryRepo.updateName(name, jmbg);
    }

    public void editSurname(String surname, String jmbg) throws InvalidSurnameFormatException {

        surnameValid(surname);
        Person person = library.getPerson(jmbg);
        person.setSurname(surname);
        libraryRepo.updateSurname(surname, jmbg);
    }

    public void editPhoneNumber(String phoneNumber, String jmbg) throws InvalidPhoneNumberFormatException {

        phoneNumberValid(phoneNumber);
        Person person = library.getPerson(jmbg);
        person.setPhoneNumber(phoneNumber);
        libraryRepo.updatePhoneNumber(phoneNumber, jmbg);
    }

    public void addMember(String name, String surname, String jmbg, String phoneNumber, String birthDate) throws
            InvalidJmbgFormatException, JmbgAlreadyExists, InvalidNameFormatException, InvalidSurnameFormatException,
            InvalidPhoneNumberFormatException, InvalidDateFormatException {

        jmbgValid(jmbg);
        jmbgExists(jmbg);
        nameValid(name);
        surnameValid(surname);
        phoneNumberValid(phoneNumber);

        try {
            LocalDate date = LocalDate.parse(birthDate);
            Member member = new Member(jmbg, name, surname, phoneNumber, date, null,
                    MemberType.REGULAR, 0, false, true);
            library.addPerson(member);
            library.notifyObservers();
            libraryRepo.addPerson(member);
            libraryRepo.addMember(member);

        } catch (DateTimeParseException e) {

            throw new InvalidDateFormatException();
        }
    }

    public void prolongIssue(String jmbg, String bookId) {
        Member member = (Member) library.getPerson(jmbg);

        for (IssuedBook issuedBook : member.getCurrentlyTakenBooks()) {

            if (issuedBook.getBookId().equals(bookId)) {
                issuedBook.prolongIssue();
                libraryRepo.prolongIssue(issuedBook);
                library.notifyObservers();
                break;
            }
        }
    }

    private void phoneNumberValid(String phoneNumber) throws InvalidPhoneNumberFormatException {
        if (!phoneNumber.matches("\\+\\d{3}\\d{9}")) {

            throw new InvalidPhoneNumberFormatException();
        }
    }

    private void surnameValid(String surname) throws InvalidSurnameFormatException {
        if (!surname.matches("[A-Z]+([a-zA-Z]+)*")) {

            throw new InvalidSurnameFormatException();
        }
    }

    private void jmbgExists(String jmbg) throws JmbgAlreadyExists {
        if (library.getPerson(jmbg) != null) {

            throw new JmbgAlreadyExists();
        }
    }

    private void jmbgValid(String jmbg) throws InvalidJmbgFormatException {
        if (!jmbg.matches("[0-2][0-9]{12}")) {

            throw new InvalidJmbgFormatException();
        }
    }

    private void nameValid(String name) throws InvalidNameFormatException {

        if (!name.matches("[A-Z][a-z]*")) {

            throw new InvalidNameFormatException();
        }
    }
}
