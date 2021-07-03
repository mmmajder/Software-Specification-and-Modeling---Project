package view.member;

import controller.AccountController;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Account;
import model.ILibraryRepo;
import model.Library;
import model.LibraryRepo;
import model.enums.MemberType;

import java.util.HashMap;

public class MembershipController {
    public Label pay6months;
    public Label price6months;
    public Label pay12months;
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
    AccountController accountController;
    Library library;
    Account account;

    public void initData(Account account) {
        library = new Library();
        libraryRepo = new LibraryRepo();
        this.account = account;
        accountController = new AccountController(library);
        libraryRepo.loadMaxIssueDays(library);
        libraryRepo.loadMaxIssuedBooks(library);
        libraryRepo.loadPriceCatalogs(library);
        libraryRepo.loadFullYearPrices(library);
        libraryRepo.loadHalfAYearPrices(library);

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

                price6months.setText(library.get6mothsPrice(memberType));
                price12months.setText(library.get12mothsPrice(memberType));

                maxNumberOfBooks.setText(library.getMaxNumberOfTakenBooks(memberType));
                maxNumberOfDays.setText(library.getMaxNumberOfIssueDays(memberType));
            }
        });
    }
}
