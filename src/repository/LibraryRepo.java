package repository;

import model.*;
import model.enums.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Date;

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

                    switch (account.getType()) {
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

        String query = "SELECT * FROM contributorRoles";

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

                Book book = new Book(bookId, BookState.valueOf(state), edition, isRestricted);
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
                m.setPendingReservation(pendingReservation);
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
            ResultSet reservations = statement.executeQuery();

            while (reservations.next()) {

                int reservationId = reservations.getInt("idr");
                String member = reservations.getString("member");
                String book = reservations.getString("book");
                LocalDate reservedOn = reservations.getDate("reservedOn").toLocalDate();

                Member m = (Member) library.getPerson(member);
                Book b = library.getBook(book);
                Reservation reservation = new Reservation(reservationId, m, b, reservedOn);
                m.setReservation(reservation);
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
                Date toDate = priceCatalogs.getDate("toDate");

                PriceCatalog catalog;
                if (toDate == null) {

                    catalog = new PriceCatalog(catalogId, fromDate, null);
                } else {
                    catalog = new PriceCatalog(catalogId, fromDate, toDate.toLocalDate());

                }
                library.addCatalog(catalog);
            }

            library.setCurrentPriceCatalog();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadPayments(Library library) {

        String query = "SELECT * FROM payments";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet payments = statement.executeQuery();

            while (payments.next()) {

                int id = payments.getInt("idp");
                String jmbg = payments.getString("member");
                LocalDate toDate = payments.getDate("toDate").toLocalDate();
                int numOfMonths = payments.getInt("numOfMonths");
                LocalDate paymentDate = payments.getDate("paymentDate").toLocalDate();

                Member member = (Member) library.getPerson(jmbg);
                Payment payment = new Payment(id, paymentDate, toDate, member, numOfMonths);
                payment.setMember(member);
                member.addPayment(payment);
                library.addPayment(payment);
            }

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
                Date returnDate = issuedBooks.getDate("returnDate");
                int prolongedIssue = issuedBooks.getInt("prolongedIssue");
                String librarian = issuedBooks.getString("librarian");
                String member = issuedBooks.getString("member");

                Book book = library.getBook(bookId);
                Librarian l = (Librarian) library.getPerson(librarian);
                Member m = (Member) library.getPerson(member);
                boolean isProlonged = prolongedIssue == 1;
                IssuedBook issuedBook;

                if (returnDate == null) {

                    issuedBook = new IssuedBook(issueDate, null, isProlonged, book, l, m);
                    m.addTakenBook(issuedBook);
                    library.addIssuedBook(issuedBook);
                } else {

                    issuedBook = new IssuedBook(issueDate, returnDate.toLocalDate(), isProlonged, book, l, m);
                    m.addReturnedBook(issuedBook);
                }
                l.addIssuedBook(issuedBook);
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
                String email = notifications.getString("account");

                Account account = library.getAccountByEmail(email);
                Notification notification = new Notification(notificationId, message, notiDate, account);
                account.addNotification(notification);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNotification(Notification notification) {
        String query = "INSERT INTO notifications VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, notification.getId());
            statement.setString(2, notification.getMessage());
            statement.setDate(3, Date.valueOf(notification.getDate()));
            statement.setString(4, notification.getAccount().getEmail());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateName(String name, String jmbg) {

        String query = "UPDATE persons SET name = ? WHERE jmbg = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, jmbg);
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSurname(String surname, String jmbg) {

        String query = "UPDATE persons SET surname = ? WHERE jmbg = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, surname);
            statement.setString(2, jmbg);
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePhoneNumber(String phoneNumber, String jmbg) {

        String query = "UPDATE persons SET phoneNumber = ? WHERE jmbg = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, phoneNumber);
            statement.setString(2, jmbg);

            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPerson(Person person) {

        String query = "INSERT INTO persons VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, person.getJMBG());
            statement.setString(2, person.getName());
            statement.setString(3, person.getSurname());
            statement.setString(4, person.getPhoneNumber());
            statement.setDate(5, Date.valueOf(person.getBirthDate()));

            if (person.getAccount() == null) {
                statement.setString(6, null);
            } else {
                statement.setString(6, person.getAccount().getEmail());
            }

            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAccount(Account account) {

        String query = "INSERT INTO accounts VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account.getEmail());
            statement.setString(2, account.getUsername());
            statement.setString(3, account.getPassword());
            statement.setString(4, account.getType().toString());
            statement.setString(5, account.getPerson().getJMBG());
            statement.setInt(6, account.isActive() ? 1 : 0);
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPayment(Payment payment) {

        String query = "INSERT INTO payments VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, payment.getPaymentId());
            statement.setString(2, payment.getMember().getJMBG());
            statement.setDate(3, Date.valueOf(payment.getValidToDate()));
            statement.setInt(4, payment.getNumOfMonths());
            statement.setDate(5, Date.valueOf(payment.getPaymentDate()));
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPendingReservation(PendingReservation pendingReservation) {

        String query = "INSERT INTO pendingReservations VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pendingReservation.getId());
            statement.setString(2, pendingReservation.getMember().getJMBG());
            statement.setString(3, pendingReservation.getEdition().getEditionId());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addReservation(Reservation reservation) {

        String query = "INSERT INTO reservations VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reservation.getId());
            statement.setString(2, reservation.getMember().getJMBG());
            statement.setString(3, reservation.getBook().getBookId());
            statement.setDate(4, Date.valueOf(reservation.getReservedOn()));
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addIssuedBook(IssuedBook issuedBook) {

        String query = "INSERT INTO issuedBooks VALUES (?, ?, NULL, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, issuedBook.getBook().getBookId());
            statement.setDate(2, Date.valueOf(issuedBook.getIssueDate()));
            statement.setInt(3, issuedBook.isProlongedIssue() ? 1 : 0);
            statement.setString(4, issuedBook.getLibrarian().getJMBG());
            statement.setString(5, issuedBook.getMember().getJMBG());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addGenre(Genre genre) {

        String query = "INSERT INTO genres VALUES (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, genre.getGenreId());
            statement.setString(2, genre.getName());
            statement.executeUpdate();

            for (Edition edition : genre.getEditions()) {

                query = "INSERT INTO editionGenres VALUES (?, ?)";
                statement = connection.prepareStatement(query);
                statement.setString(1, edition.getEditionId());
                statement.setInt(2, genre.getGenreId());
                statement.executeUpdate();

                query = "COMMIT";
                statement = connection.prepareStatement(query);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEdition(Edition edition) {

        String query = "INSERT INTO editions VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, edition.getEditionId());
            statement.setString(2, edition.getTitle());
            statement.setString(3, edition.getPublisher());
            statement.setInt(4, edition.getNumberOfPages());
            statement.setString(5, edition.getDescription());
            statement.setDate(6, Date.valueOf(edition.getPublishedDate()));
            statement.setString(7, edition.getLanguage());
            statement.setInt(8, edition.getFormat().getBookFormatId());
            statement.setString(9, edition.getImage());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addContributor(Contributor contributor) {

        String query = "INSERT INTO contributors VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, contributor.getContributorId());
            statement.setString(2, contributor.getName());
            statement.setString(3, contributor.getSurname());
            statement.setString(4, contributor.getBiography());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addContributorRole(ContributorRole role) {

        String query = "INSERT INTO contributorRoles VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, role.getEdition().getEditionId());
            statement.setInt(2, role.getContributor().getContributorId());
            statement.setString(3, role.getRole().toString());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBookSection(BookSection bookSection) {

        String query = "INSERT INTO bookSections VALUES (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, bookSection.getSectionId());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBookShelf(BookSection bookSection) {

        String query = "INSERT INTO booksPosition VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement;
            for (Bookshelf bookshelf : bookSection.getShelves()) {
                for (int row : bookshelf.getRows().keySet()) {
                    for (Book book : bookshelf.getBooks(row)) {

                        statement = connection.prepareStatement(query);
                        statement.setString(1, bookSection.getSectionId());
                        statement.setInt(2, bookshelf.getShelfId());
                        statement.setInt(3, row);
                        statement.setString(4, book.getBookId());
                        statement.executeUpdate();
                    }
                }
            }

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBook(Book book) {

        String query = "INSERT INTO books VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getBookId());
            statement.setString(2, book.getEdition().getEditionId());
            statement.setString(3, book.getState().toString());
            statement.setInt(4, book.isRestricted() ? 1 : 0);
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBookFormat(BookFormat bookFormat) {

        String query = "INSERT INTO bookFormats VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, bookFormat.getBookFormatId());
            statement.setDouble(2, bookFormat.getHeight());
            statement.setDouble(3, bookFormat.getWidth());
            statement.setDouble(4, bookFormat.getThickness());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prolongIssue(IssuedBook issueBook) {

        String query = "UPDATE issuedBooks SET prolongedIssue = ? WHERE book = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, 1);
            statement.setString(2, issueBook.getBookId());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMember(Member member) {

        String query = "INSERT INTO members VALUES (?, ?, ?, ?, ?, null, null)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, member.getJMBG());
            statement.setString(2, member.getType().toString());
            statement.setDouble(3, member.getDebt());
            statement.setInt(4, member.isMembershipPaid() ? 1 : 0);
            statement.setInt(5, member.isActive() ? 1 : 0);

            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePendingReservation(PendingReservation pendingReservation) {

        String query = "DELETE FROM pendingReservations WHERE idpr = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, pendingReservation.getId());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeReservation(Reservation reservation) {

        String query = "DELETE FROM reservations WHERE idr = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, reservation.getId());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addLibrarian(Librarian librarian) {

        String query = "INSERT INTO librarians VALUES (?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, librarian.getJMBG());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookState(Book book) {

        String query = "UPDATE books SET state = ? WHERE idb = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book.getState().toString());
            statement.setString(2, book.getBookId());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMembersPendingReservation(Member member) {

        String query = "UPDATE members SET pendingReservation = ? WHERE jmbg = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, member.getPendingReservation().getId());
            statement.setString(2, member.getJMBG());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addMembersReservation(Member member) {

        String query = "UPDATE members SET reservation = ? WHERE jmbg = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, member.getReservation().getId());
            statement.setString(2, member.getJMBG());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMembersPendingReservation(Member member) {

        String query = "UPDATE members SET pendingReservation = null WHERE jmbg = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, member.getJMBG());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMembersReservation(Member member) {

        String query = "UPDATE members SET reservation = null WHERE jmbg = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, member.getJMBG());
            statement.executeUpdate();

            query = "COMMIT";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
