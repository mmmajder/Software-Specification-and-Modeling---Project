package model;

public interface ILibraryRepo {
    void loadAccounts(Library library);
    void loadPersons(Library library);
    void loadEditions(Library library);
    void loadContributors(Library library);
    void loadContributorRoles(Library library);
    void loadTags(Library library);
    void loadGenres(Library library);
    void loadBooks(Library library);
}
