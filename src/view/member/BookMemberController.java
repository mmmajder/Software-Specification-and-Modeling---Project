package view.member;

import controller.EditionController;
import controller.MemberReservationController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import model.Account;
import model.Edition;
import model.Library;
import model.Member;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.MemberAlreadyHasPendingRequestException;
import utils.exceptions.MemberAlreadyHasReservedBook;

import java.io.IOException;

public class BookMemberController implements Observer {
    public Label lblTitle;
    public Label lblAuthor;
    public Label lblPublishedDate;
    public Label lblLanguage;
    public Label lblPublisher;
    public Label lblNumberOfPages;
    public Label lblTranslation;
    public Label lblIllustration;
    public Label lblGenre;
    public Text txtDescription;
    public Button btnReserve;

    MemberController memberController;
    EditionController editionController;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;
    Edition edition;

    @FXML
    public void backToBooks() throws IOException {
        memberController.switchToBooks();
    }

    @FXML
    private void createAlert(String text) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Alert");
        a.setContentText(text);
        a.show();
    }

    @FXML
    public void initData(Edition edition, MemberController memberController, Account account) {
        this.edition = edition;
        this.memberController = memberController;
        this.library = new Library();
        this.libraryRepo = new LibraryRepo();
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadEditions(library);
        libraryRepo.loadPendingReservations(library);
        libraryRepo.loadReservations(library);
        libraryRepo.loadIssuedBooks(library);
        library.addObserver(this);
        this.account = library.getAccountByEmail(account.getEmail());
        editionController = new EditionController(library);
        lblTitle.setText(edition.getTitle());
        lblAuthor.setText(editionController.getAuthorName(edition));
        final Tooltip authorBiography = new Tooltip();
        authorBiography.setText(edition.getAuthor().getBiography());
        if (((Member) account.getPerson()).getReservation() != null &&
                ((Member) account.getPerson()).getReservedBook().getEdition() == edition) {
            btnReserve.setText("RESERVED");
        } else if (((Member) account.getPerson()).getPendingReservation() != null &&
                ((Member) account.getPerson()).getPendingReservation().getEdition() == edition) {
            btnReserve.setText("PENDING");
        } else {
            btnReserve.setText("RESERVE");
        }
        lblAuthor.setTooltip(authorBiography);
        txtDescription.setText(edition.getDescription());
        lblPublishedDate.setText("Published date: " + edition.getPublishedDate());
        lblLanguage.setText("Language: " + edition.getLanguage());
        lblPublisher.setText("Publisher: " + edition.getPublisher());
        lblNumberOfPages.setText("Name of pages: " + edition.getNumberOfPages());
        lblTranslation.setText("Translation: " + editionController.getTranslatorsStr(edition));
        lblIllustration.setText("Illustration: " + editionController.getIllustratorsStr(edition));
        lblGenre.setText("Genre: " + editionController.getGenresConcatenated(edition));
    }

    public void reserve() {
        try {
            if (account.isActive()) {
                if (btnReserve.getText().equals("RESERVE")) {
                    MemberReservationController memberReservationController = new MemberReservationController(library);
                    memberReservationController.sendReservationRequest((Member) account.getPerson(), edition);
                    btnReserve.setText("PENDING");
                } else {
                    createAlert("You already send this request.");
                }
            } else {
                createAlert("You have to pay membership first.");
            }
        } catch (MemberAlreadyHasReservedBook memberAlreadyHasReservedBook) {
            createAlert("Member already have 1 reserved book.");
        } catch (MemberAlreadyHasPendingRequestException memberAlreadyHasPendingRequestException) {
            createAlert("Member already have 1 pending request.");
        }
    }

    @Override
    public void updatePerformed() {
        if (((Member) account.getPerson()).getReservedBook().getEdition() == edition) {
            btnReserve.setText("RESERVED");
        } else if ((((Member) account.getPerson()).getPendingReservation().getEdition() == edition)) {
            btnReserve.setText("PENDING");
        } else {
            btnReserve.setText("RESERVE");
        }
    }
}