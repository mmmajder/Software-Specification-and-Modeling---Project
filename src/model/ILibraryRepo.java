package model;

import model.enums.AccountType;

public interface ILibraryRepo {
    void loadAccounts(Library library);
    void loadPerson(Account account, String jmbg, AccountType type);
}
