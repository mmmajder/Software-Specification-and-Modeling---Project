package controller;

import model.*;
import utils.exceptions.InvalidNameFormatException;
import utils.exceptions.InvalidPhoneNumberFormatException;
import utils.exceptions.InvalidSurnameFormatException;

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

    public void editJmbg(String jmbg) {

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

    private boolean jmbgValid(String jmbg) {
        return jmbg.matches("[0-2][0-9]{12}");
    }

    private boolean nameValid(String name) {

        return name.matches("[A-Z][a-z]*");
    }
}
