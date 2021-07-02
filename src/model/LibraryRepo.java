package model;

import model.enums.AccountType;
import model.enums.ContributorType;
import model.enums.MemberType;
import model.enums.SampleState;

import java.sql.*;
import java.time.LocalDate;

public class LibraryRepo implements ILibraryRepo {

    String dbURL = "jdbc:oracle:thin:@localhost:1521:XE";
    String username = "EA14789";
    String password = "sims";
    Connection connection = null;

    public LibraryRepo() {

        establishConnection();
    }

    private void establishConnection() {
        try {

            this.connection = DriverManager.getConnection(this.dbURL, this.username, this.password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadAccounts(Library library) {

        String query = "SELECT * FROM accounts";
        try {

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet accounts = statement.executeQuery();

            while (accounts.next()) {

                String email = accounts.getString("email");
                String username = accounts.getString("username");
                String password = accounts.getString("password");
                String accType = accounts.getString("type");
                int active = accounts.getInt("active");

                if (active == 1) {
                    Account account;
                    AccountType type = AccountType.valueOf(accType);

                    account = new Account(username, password, email, type, null, true);
                    library.addAccount(account);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadPersons(Library library) {

        String query = "SELECT * FROM persons";

        try {

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet persons = statement.executeQuery();
            while (persons.next()) {

                String jmbg = persons.getString("jmbg");
                String name = persons.getString("name");
                String surname = persons.getString("surname");
                String phoneNumber = persons.getString("phoneNumber");
                LocalDate birthDate = persons.getDate("dateOfBirth").toLocalDate();
                String email = persons.getString("account");

                Person person = null;

                if (email == null) {

                    person = loadMember(jmbg, name, surname, phoneNumber, birthDate);

                } else {

                    Account account = library.getAccountByEmail(email);

                    switch(account.getType()) {
                        case ADMIN:
                            person = new Admin(name, surname, jmbg, phoneNumber, birthDate, null);
                            break;
                        case MEMBER:
                            person = loadMember(jmbg, name, surname, phoneNumber, birthDate);
                            break;
                        case LIBRARIAN:
                            person = new Librarian(name, surname, jmbg, phoneNumber, birthDate, null);
                            break;
                    }

                    account.setPerson(person);
                    assert person != null;
                    person.setAccount(account);
                }

                library.addPerson(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Person loadMember(String jmbg, String name, String surname, String phoneNumber, LocalDate birthDate) {
        try {
            String query = "SELECT * FROM members WHERE jmbg = " + jmbg;
            PreparedStatement memberStatement = connection.prepareStatement(query);
            ResultSet member = memberStatement.executeQuery();
            member.next();

            String type = member.getString("type");
            double debt = member.getDouble("debt");
            int memberShipPaid = member.getInt("memberShipPaid");
            int active = member.getInt("active");
            boolean isMembershipPaid = memberShipPaid == 1;
            boolean isActive = active == 1;

            return new Member(name, surname, jmbg, phoneNumber, birthDate, null,
                    MemberType.valueOf(type), debt, isMembershipPaid, isActive);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void loadEditions(Library library) {

        String query = "SELECT * FROM editions";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet editions = statement.executeQuery();

            while (editions.next()) {

                String editionId = editions.getString("ide");
                String title = editions.getString("title");
                String publisher = editions.getString("publisher");
                int numOfPages = editions.getInt("numOfPages");
                String description = editions.getString("description");
                LocalDate publishedDate = editions.getDate("publishedDate").toLocalDate();
                String language = editions.getString("language");
                int format = editions.getInt("format");
                String image = editions.getString("image");

                query = "SELECT * FROM bookFormats WHERE idbf = " + format;
                PreparedStatement bookFormatStatement = connection.prepareStatement(query);
                ResultSet formats = bookFormatStatement.executeQuery(query);
                formats.next();

                double height = formats.getDouble("height");
                double width = formats.getDouble("width");
                double thickness = formats.getDouble("thickness");

                BookFormat bookFormat = new BookFormat(format, height, width, thickness);

                Edition edition = new Edition(editionId, title, publisher, numOfPages, description, publishedDate,
                        language, image, bookFormat);
                library.addEdition(edition);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadContributors(Library library) {

        String query = "SELECT * FROM contributors";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet contributors = statement.executeQuery();

            while (contributors.next()) {

                int idc = contributors.getInt("idc");
                String name = contributors.getString("name");
                String surname = contributors.getString("surname");
                String biography = contributors.getString("biography");

                Contributor contributor = new Contributor(idc, name, surname, biography);
                library.addContributor(contributor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadContributorRoles(Library library) {

        String query = "SELECT * FROM editionContributors";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet contributorRoles = statement.executeQuery();

            while (contributorRoles.next()) {

                String editionId = contributorRoles.getString("ide");
                int contributorId = contributorRoles.getInt("idc");
                String contributorType = contributorRoles.getString("type");

                Edition edition = library.getEdition(editionId);
                Contributor contributor = library.getContributor(contributorId);

                ContributorRole contributorRole = new ContributorRole(ContributorType.valueOf(contributorType),
                        contributor, edition);
                edition.addContributorRole(contributorRole);
                contributor.addContributorRole(contributorRole);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadTags(Library library) {

        String query = "SELECT * FROM editionTags";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet tags = statement.executeQuery();

            while (tags.next()) {
                String editionId = tags.getString("ide");
                String tag = tags.getString("tag");

                Edition edition = library.getEdition(editionId);
                edition.addTag(tag);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadGenres(Library library) {
        String query = "SELECT * FROM editionGenres";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet genres = statement.executeQuery();

            while (genres.next()) {
                String editionId = genres.getString("edition");
                int genreId = genres.getInt("genre");

                query = "SELECT * FROM genres WHERE idg = " + genreId;
                PreparedStatement genreStatement = connection.prepareStatement(query);
                ResultSet result = genreStatement.executeQuery();
                result.next();

                String genreName = result.getString("name");
                Genre genre = new Genre(genreId, genreName);
                library.addGenre(genre);

                Edition edition = library.getEdition(editionId);
                edition.addGenre(genre);
                genre.addEdition(edition);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadBooks(Library library) {

        String query = "SELECT * FROM books";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet books = statement.executeQuery();

            while (books.next()) {
                String bookId = books.getString("idb");
                String editionId = books.getString("edition");
                String state = books.getString("state");
                int restriction = books.getInt("isRestricted");

                boolean isRestricted = restriction == 1;

                Edition edition = library.getEdition(editionId);

                Book book = new Book(bookId, SampleState.valueOf(state), edition, isRestricted);
                edition.addBook(book);

                library.addBook(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadMaxIssueDays(Library library) {

        String query = "SELECT * FROM maxIssueDays";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String memberType = resultSet.getString("type");
                int days = resultSet.getInt("days");

                library.addIssueDayConstraint(MemberType.valueOf(memberType), days);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadMaxIssuedBooks(Library library) {

        String query = "SELECT * FROM maxIssuedBooks";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                String memberType = resultSet.getString("type");
                int limit = resultSet.getInt("limit");

                library.addIssuedBooksConstraint(MemberType.valueOf(memberType), limit);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadPendingReservations(Library library) {

        String query = "SELECT * FROM pendingReservations";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet pendingReservations = statement.executeQuery();

            while (pendingReservations.next()) {

                int pendingReservationsId = pendingReservations.getInt("idpr");
                String member = pendingReservations.getString("member");
                String edition = pendingReservations.getString("edition");

                Member m = (Member) library.getPerson(member);
                Edition e = library.getEdition(edition);
                PendingReservation pendingReservation = new PendingReservation(pendingReservationsId, m, e);
                library.addPendingReservation(pendingReservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadReservations(Library library) {
        String query = "SELECT * FROM reservations";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet reservedBooks = statement.executeQuery();

            while (reservedBooks.next()) {

                int reservationId = reservedBooks.getInt("idpr");
                String member = reservedBooks.getString("member");
                String book = reservedBooks.getString("book");

                Member m = (Member) library.getPerson(member);
                Book b = library.getBook(book);
                Reservation reservation = new Reservation(reservationId, m, b);
                library.addReservation(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadPriceCatalogs(Library library) {
        String query = "SELECT * FROM priceCatalogs";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet priceCatalogs = statement.executeQuery();

            while (priceCatalogs.next()) {

                int catalogId = priceCatalogs.getInt("catalogId");
                LocalDate fromDate = priceCatalogs.getDate("fromDate").toLocalDate();
                LocalDate toDate = priceCatalogs.getDate("toDate").toLocalDate();

                PriceCatalog catalog = new PriceCatalog(catalogId, fromDate, toDate);
                library.addCatalog(catalog);
            }

            library.setCurrentPriceCatalog();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadHalfAYearPrices(Library library) {

        String query = "SELECT * FROM catalogHalfAYearPrices";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int catalogId = resultSet.getInt("catalog");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");

                PriceCatalog catalog = library.getPriceCatalog(catalogId);
                catalog.addHalfAYearPrice(MemberType.valueOf(type), price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFullYearPrices(Library library) {
        String query = "SELECT * FROM catalogFullYearPrices";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                int catalogId = resultSet.getInt("catalog");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");

                PriceCatalog catalog = library.getPriceCatalog(catalogId);
                catalog.addFullYearPrice(MemberType.valueOf(type), price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadIssuedBooks(Library library) {
        String query = "SELECT * FROM issuedBooks";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet issuedBooks = statement.executeQuery();

            while (issuedBooks.next()) {

                String bookId = issuedBooks.getString("book");
                LocalDate issueDate = issuedBooks.getDate("issueDate").toLocalDate();
                LocalDate returnDate = issuedBooks.getDate("returnDate").toLocalDate();
                int prolongedIssue = issuedBooks.getInt("prolongedIssue");
                String librarian = issuedBooks.getString("librarian");
                String member = issuedBooks.getString("member");

                Book book = library.getBook(bookId);
                Librarian l = (Librarian) library.getPerson(librarian);
                Member m = (Member) library.getPerson(member);
                boolean isProlonged = prolongedIssue == 1;

                IssuedBook issuedBook = new IssuedBook(issueDate, returnDate, isProlonged, book, l, m);

                l.addIssuedBook(issuedBook);
                if (book.getState() == SampleState.TAKEN) {
                    m.addTakenBook(issuedBook);
                    library.addIssuedBook(issuedBook);
                } else {
                    m.addReturnedBook(issuedBook);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadNotifications(Library library) {
        String query = "SELECT * FROM notifications";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet notifications = statement.executeQuery();

            while (notifications.next()) {

                String notificationId = notifications.getString("idn");
                String message = notifications.getString("message");
                LocalDate notiDate = notifications.getDate("notiDate").toLocalDate();
                String member = notifications.getString("member");

                Member m = (Member) library.getPerson(member);
                Notification notification = new Notification(notificationId, message, notiDate, m);
                m.addNotification(notification);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
