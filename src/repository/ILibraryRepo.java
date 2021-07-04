package repository;

import model.*;

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
    void loadReservations(Library library);
    void loadPriceCatalogs(Library library);
    void loadPayments(Library library);
    void loadHalfAYearPrices(Library library);
    void loadFullYearPrices(Library library);
    void loadIssuedBooks(Library library);
    void loadNotifications(Library library);
    void addNotification(Notification notification);
    void updateName(String name, String jmbg);
    void updateSurname(String surname, String jmbg);
    void updatePhoneNumber(String phoneNumber, String jmbg);
    void addPerson(Person person);
    void addAccount(Account account);
    void addPayment(Payment payment);
    void addPendingReservation(PendingReservation pendingReservation);
    void addReservation(Reservation reservation);
    void addIssuedBook(IssuedBook issuedBook);
    void addGenre(Genre genre);
    void addEdition(Edition edition);
    void addContributor(Contributor contributor);
    void addContributorRole(ContributorRole role);
    void addBookSection(BookSection bookSection);
    void addBookShelf(BookSection bookSection);
    void addBook(Book book);
    void addBookFormat(BookFormat bookFormat);
    void prolongIssue(IssuedBook issueBook);
    void addMember(Member member);
    void removePendingReservation(PendingReservation pendingReservation);
    void removeReservation(Reservation reservation);
}