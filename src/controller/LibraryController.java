package controller;

import model.Librarian;
import model.Library;
import model.enums.AccountType;

import java.util.List;
import java.util.stream.Collectors;

public class LibraryController {

    private Library library;

    public LibraryController(Library library) {
        this.library = library;
    }

    public boolean isLibrarian(String jmbg){
        return getLibrarians().stream()
                .anyMatch(librarian -> librarian.getJMBG().equals(jmbg));
    }

    public List<Librarian> getLibrarians(){
        return library.getAccounts().stream()
                .filter(account -> account.getType() == AccountType.LIBRARIAN)
                .map(account -> account.getPerson())
                .map(person -> (Librarian) person)
                .collect(Collectors.toList());
    }
}
