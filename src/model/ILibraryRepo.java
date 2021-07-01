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
    void loadMaxIssueDays(Library library);
    void loadMaxIssuedBooks(Library library);
    void loadPendingReservations(Library library);
    void loadReservedBooks(Library library);
    void loadPriceCatalogs(Library library);
    void loadHalfAYearPrices(Library library);
    void loadFullYearPrices(Library library);
    void loadIssuedBooks(Library library);
    void loadNotifications(Library library);
}
