package view.member;

import controller.AccountController;
import controller.MembershipControler;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Account;
import model.Member;
import observer.Observer;
import repository.ILibraryRepo;
import model.Library;
import repository.LibraryRepo;
import model.enums.MemberType;
import utils.exceptions.InvalidTransactionException;

public class MembershipController implements Observer {
    public Label price6months;
    public Label price12months;

    public Label maxNumberOfBooks;
    public Label maxNumberOfDays;
    public Label status;

    public RadioButton regularRB;
    public RadioButton studentRB;
    public RadioButton pupilRB;
    public RadioButton preschoolerRB;
    public RadioButton retiredRB;
    public RadioButton privilegedRB;

    public Pane panel6;
    public Pane panel12;

    ILibraryRepo libraryRepo;
    MembershipControler membershipControler;
    AccountController accountController;
    Library library;
    Account account;

    public void initData(Account account) {
        library = new Library();
        libraryRepo = new LibraryRepo();
        this.account = account;
        membershipControler = new MembershipControler(library);
        accountController = new AccountController(library);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadPersons(library);
        libraryRepo.loadPayments(library);
        libraryRepo.loadMaxIssueDays(library);
        libraryRepo.loadMaxIssuedBooks(library);
        libraryRepo.loadPriceCatalogs(library);
        libraryRepo.loadFullYearPrices(library);
        libraryRepo.loadHalfAYearPrices(library);
        library.addObserver(this);

        status.setText(accountController.getMembershipStatus(account));
        if (accountController.getMembershipExpirationDate(account.getPerson()) == null) {
            status.setTextFill(Paint.valueOf("#CD113B"));
        } else {
            status.setTextFill(Paint.valueOf("#ffffff"));
        }

        DropShadow dropShadow = new DropShadow(6, 0, 5, Color.GRAY);
        panel6.setEffect(dropShadow);
        panel12.setEffect(dropShadow);

        final ToggleGroup group = new ToggleGroup();
        regularRB.setToggleGroup(group);
        studentRB.setToggleGroup(group);
        pupilRB.setToggleGroup(group);
        preschoolerRB.setToggleGroup(group);
        retiredRB.setToggleGroup(group);
        privilegedRB.setToggleGroup(group);

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                MemberType memberType = MemberType.valueOf(((RadioButton) group.getSelectedToggle()).getText());

                price6months.setText(library.getCurrentCatalog().getPrice(memberType, 6) + " RSD");
                price12months.setText(library.getCurrentCatalog().getPrice(memberType, 12) + " RSD");

                maxNumberOfBooks.setText("Number of books you can issue is " + library.getMaxIssuedBooks().get(memberType) + ".");
                maxNumberOfDays.setText("Number of days you can keep your books is " + library.getMaxIssueDays().get(memberType) + ".");
            }
        });
        regularRB.setSelected(true);
    }

    public void pay12months() throws InvalidTransactionException {
        membershipControler.payMembership((Member) account.getPerson(), 12);
    }

    public void pay6months() throws InvalidTransactionException {
        membershipControler.payMembership((Member) account.getPerson(), 6);
    }

    @Override
    public void updatePerformed() {
        status.setText(accountController.getMembershipStatus(account));
    }
}
