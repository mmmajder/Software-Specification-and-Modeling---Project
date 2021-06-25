package model;

import model.enums.AccountType;

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

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String accType = resultSet.getString("type");
                String jmbg = resultSet.getString("person");

                Account account;
                AccountType type;

                if (accType.equalsIgnoreCase("ADMIN")) {
                    type = AccountType.ADMIN;

                } else if (accType.equalsIgnoreCase("LIBRARIAN")) {
                    type = AccountType.LIBRARIAN;

                } else {
                    type = AccountType.MEMBER;
                }

                account = new Account(username, password, email, type, null);
                loadPerson(account, jmbg, type);
                library.addAccount(account);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadPerson(Account account, String jmbg, AccountType type) {

        String query = "SELECT * FROM persons WHERE jmbg = " + jmbg;

        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String phoneNumber = resultSet.getString("phoneNumber");
                LocalDate birthDate = resultSet.getDate("dateOfBirth").toLocalDate();

                Person person = null;

                switch(type) {
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
