package controller;

import model.ILibraryRepo;
import model.Library;
import model.LibraryRepo;
import model.Person;
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

    public void changeName(String name, String jmbg) throws InvalidNameFormatException {

        if (nameValid(name)) {

            Person person = library.getPerson(jmbg);
            person.setName(name);
            libraryRepo.updateName(name, jmbg);
        }

        throw new InvalidNameFormatException();
    }

    public void changeSurname(String surname, String jmbg) throws InvalidSurnameFormatException {

        if (surnameValid(surname)) {

            Person person = library.getPerson(jmbg);
            person.setSurname(surname);
            libraryRepo.updateSurname(surname, jmbg);
        }

        throw new InvalidSurnameFormatException();
    }

    public void changePhoneNumber(String phoneNumber, String jmbg) throws InvalidPhoneNumberFormatException {

        if (phoneNumberValid(phoneNumber)) {

            Person person = library.getPerson(jmbg);
            person.setPhoneNumber(phoneNumber);
            libraryRepo.updatePhoneNumber(phoneNumber, jmbg);
        }

        throw new InvalidPhoneNumberFormatException();
    }

    private boolean phoneNumberValid(String phoneNumber) {
        return phoneNumber.matches("\\+\\d{3}\\d{9}");
    }

    private boolean surnameValid(String surname) {
        return surname.matches("[A-Z]+([ '-][a-zA-Z]+)*");
    }

    private boolean nameValid(String name) {

        return name.matches("[A-Z][a-z]*");
    }
}
