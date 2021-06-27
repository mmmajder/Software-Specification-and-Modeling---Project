package model;

import model.enums.AccountType;
import model.enums.ContributorType;
import model.enums.Genre;
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
            System.out.println("Connection successful");

        } catch (SQLException e) {

            System.out.println("Connection cannot be established");
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

                Account account;
                AccountType type = AccountType.valueOf(accType);

                account = new Account(username, password, email, type, null);
                library.addAccount(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadPersons(Library library) {

        for (Account account : library.getAccounts()) {
            String query = "SELECT * FROM persons WHERE account = ?";

            try {

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, account.getEmail());
                ResultSet persons = statement.executeQuery();
                persons.next();

                String jmbg = persons.getString("jmbg");
                String name = persons.getString("name");
                String surname = persons.getString("surname");
                String phoneNumber = persons.getString("phoneNumber");
                LocalDate birthDate = persons.getDate("dateOfBirth").toLocalDate();

                Person person = null;

                switch(account.getType()) {
                    case ADMIN:
                        person = new Admin(name, surname, jmbg, phoneNumber, birthDate, null);
                        break;
                    case MEMBER:
                        person = new Member(name, surname, jmbg, phoneNumber, birthDate, null);
                        break;
                    case LIBRARIAN:
                        person = new Librarian(name, surname, jmbg, phoneNumber, birthDate, null);
                        break;
                }

                account.setPerson(person);
                person.setAccount(account);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
                String genre = editions.getString("genre");
                LocalDate publishedDate = editions.getDate("publishedDate").toLocalDate();
                String language = editions.getString("language");
                int format = editions.getInt("format");

                query = "SELECT * FROM bookFormats WHERE idbf = " + format;
                ResultSet formats = statement.executeQuery(query);
                formats.next();

                double height = formats.getDouble("height");
                double width = formats.getDouble("width");
                double thickness = formats.getDouble("thickness");

                BookFormat bookFormat = new BookFormat(format, height, width, thickness);

                Edition edition = new Edition(editionId, title, publisher, numOfPages, description,
                        Genre.valueOf(genre), publishedDate, language, null, bookFormat);
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
}
