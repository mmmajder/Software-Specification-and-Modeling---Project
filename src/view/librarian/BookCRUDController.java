package view.librarian;

import controller.IssuedBookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.*;
import model.enums.BookState;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import view.librarian.model.BookEditionTable;
import view.librarian.model.BookSampleTable;

import java.io.IOException;
import java.time.LocalDate;

public class BookCRUDController implements Observer {
    ObservableList<BookEditionTable> dataEditionTable;
    ObservableList<BookSampleTable> dataSampleTable;
    public TableView<BookEditionTable> editionTable;
    public TableView<BookSampleTable> sampleTable;
    private Edition edition;
    Library library;
    ILibraryRepo libraryRepo;
    LibrarianController librarianController;
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
        editionTable.getColumns().clear();
        sampleTable.getColumns().clear();
        TableColumn<BookEditionTable, String> colId = new TableColumn<BookEditionTable, String>("Id") {
            {
                prefWidthProperty().bind(editionTable.widthProperty().multiply(0.3));
            }
        };
        editionTable.getColumns().add(colId);

        TableColumn<BookEditionTable, BookState> colState = new TableColumn<BookEditionTable, BookState>("State") {
            {
                prefWidthProperty().bind(editionTable.widthProperty().multiply(0.4));
            }
        };
        editionTable.getColumns().add(colState);

        TableColumn<BookEditionTable, String> colRestricted = new TableColumn<BookEditionTable, String>("Restricted") {
            {
                prefWidthProperty().bind(editionTable.widthProperty().multiply(0.3));
            }
        };
        editionTable.getColumns().add(colRestricted);


        TableColumn<BookSampleTable, LocalDate> colIssueDate = new TableColumn<BookSampleTable, LocalDate>("Issue date") {
            {
                prefWidthProperty().bind(sampleTable.widthProperty().multiply(0.25));
            }
        };
        sampleTable.getColumns().add(colIssueDate);

        TableColumn<BookSampleTable, LocalDate> colReturnedDate = new TableColumn<BookSampleTable, LocalDate>("Returned date") {
            {
                prefWidthProperty().bind(sampleTable.widthProperty().multiply(0.25));
            }
        };
        sampleTable.getColumns().add(colReturnedDate);

        TableColumn<BookSampleTable, String> colStatus = new TableColumn<BookSampleTable, String>("Status") {
            {
                prefWidthProperty().bind(sampleTable.widthProperty().multiply(0.25));
            }
        };
        sampleTable.getColumns().add(colStatus);

        TableColumn<BookSampleTable, String> colMember = new TableColumn<BookSampleTable, String>("Member") {
            {
                prefWidthProperty().bind(sampleTable.widthProperty().multiply(0.25));
            }
        };
        sampleTable.getColumns().add(colMember);

        colId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colState.setCellValueFactory(new PropertyValueFactory<>("state"));
        colRestricted.setCellValueFactory(new PropertyValueFactory<>("isRestricted"));

        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("state"));
        colMember.setCellValueFactory(new PropertyValueFactory<>("member"));
        dataEditionTable = getSamples();
        editionTable.setItems(dataEditionTable);
        editionTable.setOnMouseClicked(e -> loadSamples());
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
            Book sample = library.getBook(row.getBookId());
            for (IssuedBook issuedBook : sample.getIssueHistory()) {
                dataSampleTable.add(new BookSampleTable(issuedBook.getIssueDate(), issuedBook.getReturnDate(),
                        issuedBookController.getIssuedBookState(issuedBook), issuedBook.getMember().getFullName()));
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

    public void addSample() {
        dataEditionTable.add(new BookEditionTable(null, null, false));
        // add database connection
    }

    public void removeSample() {
        editionTable.getItems().remove(editionTable.getSelectionModel().getSelectedItem());
        // add database connection
    }
}
