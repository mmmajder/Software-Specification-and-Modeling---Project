package view.librarian;

import controller.IssuedBookController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.enums.BookState;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import view.librarian.model.BookEditionTable;
import view.librarian.model.BookSampleTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;

import java.io.IOException;
import java.time.LocalDate;

public class BookCRUDController implements Observer {
    ObservableList<BookEditionTable> dataEditionTable;
    ObservableList<BookSampleTable> dataSampleTable;
    public TableView<BookEditionTable> editionTable;
    public TableView<BookSampleTable> sampleTable;
    public TableColumn id;
    public TableColumn state;
    public TableColumn restricted;
    public TableColumn issueDate;
    public TableColumn returnedDate;
    public TableColumn status;
    public TableColumn member;
    private Edition edition;
    Library library;
    ILibraryRepo libraryRepo;
    LibrarianController librarianController;
    BookLibrarianController editionController;
    BorderPane mainBorderPane;
    IssuedBookController issuedBookController;
    Account account;

    @FXML
    public void backToEdition() throws IOException {
        FXMLLoader bookLoader = new FXMLLoader(getClass().getResource("../../fxml/librarian/bookLibrarian.fxml"));
        Parent bookScene = bookLoader.load();
        BookLibrarianController bookLibrarianController = bookLoader.getController();
        bookLibrarianController.initData(edition, mainBorderPane, librarianController, account);
        mainBorderPane.setCenter(bookScene);
    }

    public void initData(Edition edition, BorderPane mainBorderPane, LibrarianController librarianController, Account account) {
        this.edition = edition;
        this.account = account;
        this.librarianController = librarianController;
        this.mainBorderPane = mainBorderPane;
        this.library = new Library();
        library.addObserver(this);
        libraryRepo = new LibraryRepo();
        libraryRepo.loadContributors(library);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadBooks(library);
        libraryRepo.loadIssuedBooks(library);
        issuedBookController = new IssuedBookController(library);
        TableColumn colId = new TableColumn("ID") {
            {
                prefWidthProperty().bind(editionTable.widthProperty().multiply(0.3));
            }
        };
        editionTable.getColumns().add(colId);

        TableColumn colState = new TableColumn("State") {
            {
                prefWidthProperty().bind(editionTable.widthProperty().multiply(0.4));
            }
        };
        editionTable.getColumns().add(colState);

        TableColumn colRestricted = new TableColumn("Restricted") {
            {
                prefWidthProperty().bind(editionTable.widthProperty().multiply(0.3));
            }
        };
        editionTable.getColumns().add(colRestricted);


        TableColumn colIssueDate = new TableColumn("Issue date") {
            {
                prefWidthProperty().bind(sampleTable.widthProperty().multiply(0.25));
            }
        };
        sampleTable.getColumns().add(colIssueDate);

        TableColumn colReturnedDate = new TableColumn("Returned date") {
            {
                prefWidthProperty().bind(sampleTable.widthProperty().multiply(0.25));
            }
        };
        sampleTable.getColumns().add(colReturnedDate);

        TableColumn colStatus = new TableColumn("Status") {
            {
                prefWidthProperty().bind(sampleTable.widthProperty().multiply(0.25));
            }
        };
        sampleTable.getColumns().add(colStatus);

        TableColumn colMember = new TableColumn("Member") {
            {
                prefWidthProperty().bind(sampleTable.widthProperty().multiply(0.25));
            }
        };
        sampleTable.getColumns().add(colMember);

        colId.setCellValueFactory(new PropertyValueFactory<BookEditionTable, String>("bookId"));
        colState.setCellValueFactory(new PropertyValueFactory<BookEditionTable, BookState>("state"));
        colRestricted.setCellValueFactory(new PropertyValueFactory<BookEditionTable, Boolean>("isRestricted"));

        colIssueDate.setCellValueFactory(new PropertyValueFactory<BookSampleTable, LocalDate>("issueDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<BookSampleTable, LocalDate>("returnedDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<BookSampleTable, String>("state"));
        colMember.setCellValueFactory(new PropertyValueFactory<BookSampleTable, String>("member"));
        dataEditionTable = getSamples();
        editionTable.setItems(dataEditionTable);
        editionTable.setOnMouseClicked(e -> {
            loadSamples();
        });
    }

    private ObservableList<BookEditionTable> getSamples() {
        ObservableList<BookEditionTable> list = FXCollections.observableArrayList();
        for (Book book : edition.getBooks()) {
            list.add(new BookEditionTable(book.getBookId(), book.getState(), book.isRestricted()));
        }
        return list;
    }

    private void loadSamples() {
        if (editionTable.getSelectionModel().getSelectedItem().getBookId() == null) {
            return;
        }
        if (dataSampleTable != null) {
            dataSampleTable.clear();
        }
        for (BookEditionTable row : editionTable.getSelectionModel().getSelectedItems()) {
            for (int i = 1; i <= 1; i++) {
                Book sample = library.getBook(row.getBookId());
                for (IssuedBook issuedBook : sample.getIssueHistory()) {
                    dataSampleTable.add(new BookSampleTable(issuedBook.getIssueDate(), issuedBook.getReturnDate(), issuedBookController.getIssuedBookState(issuedBook), issuedBook.getMember().getName() + " " + issuedBook.getMember().getSurname()));
                }
            }
        }
    }

    @Override
    public void updatePerformed() {
        dataEditionTable = getSamples();
        editionTable.setItems(dataEditionTable);
        dataSampleTable.clear();
        sampleTable.setItems(dataSampleTable);

    }

    public void addSample(MouseEvent event) {
        dataEditionTable.add(new BookEditionTable(null, null, false));
        // add database connection
    }

    public void removeSample(MouseEvent event) {
        editionTable.getItems().remove(editionTable.getSelectionModel().getSelectedItem());
        // add database connection
    }
}
