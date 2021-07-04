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

        if (nameValid(name)) {

            Person person = library.getPerson(jmbg);
            person.setName(name);
            libraryRepo.updateName(name, jmbg);
        } else {

            throw new InvalidNameFormatException();
        }
    }

    public void editSurname(String surname, String jmbg) throws InvalidSurnameFormatException {

        if (surnameValid(surname)) {

            Person person = library.getPerson(jmbg);
            person.setSurname(surname);
            libraryRepo.updateSurname(surname, jmbg);
        } else {

            throw new InvalidSurnameFormatException();
        }
    }

    public void editPhoneNumber(String phoneNumber, String jmbg) throws InvalidPhoneNumberFormatException {

        if (phoneNumberValid(phoneNumber)) {

            Person person = library.getPerson(jmbg);
            person.setPhoneNumber(phoneNumber);
            libraryRepo.updatePhoneNumber(phoneNumber, jmbg);
        } else {

            throw new InvalidPhoneNumberFormatException();
        }
    }

    public void addMember(String name, String surname, String jmbg, String phoneNumber, String birthDate) throws
            InvalidJmbgFormatException, JmbgAlreadyExists, InvalidNameFormatException, InvalidSurnameFormatException,
            InvalidPhoneNumberFormatException, InvalidDateFormatException {

        if (jmbgValid(jmbg)) {

            if (!jmbgExists(jmbg)) {

                if (nameValid(name)) {

                    if (surnameValid(surname)) {

                        if (phoneNumberValid(phoneNumber)) {

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
                        } else {
                            throw new InvalidPhoneNumberFormatException();
                        }
                    } else {
                        throw new InvalidSurnameFormatException();
                    }
                } else {
                    throw new InvalidNameFormatException();
                }
            } else {
                throw new JmbgAlreadyExists();
            }
        } else {
            throw new InvalidJmbgFormatException();
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

    private boolean phoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("\\+\\d{3}\\d{9}");
    }

    private boolean surnameValid(String surname) {
        return surname.matches("[A-Z]+([a-zA-Z]+)*");
    }

    private boolean jmbgExists(String jmbg) {
        return library.getPerson(jmbg) != null;
    }

    private boolean jmbgValid(String jmbg) {
        return jmbg.matches("[0-2][0-9]{12}");
    }

    private boolean nameValid(String name) {

        return name.matches("[A-Z][a-z]*");
    }
}
