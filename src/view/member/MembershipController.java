package view.member;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    public RadioButton regularRB;
    public RadioButton studentRB;
    public RadioButton pupilRB;
    public RadioButton preschoolerRB;
    public RadioButton retiredRB;
    public RadioButton privilegedRB;

    public Pane panel6;
    public Pane panel12;

    ILibraryRepo libraryRepo;
    Library library;
    Account account;

    public void initData(Account account) {
        library = new Library();
        libraryRepo = new LibraryRepo();
        this.account = account;
        libraryRepo.loadMaxIssueDays(library);
        libraryRepo.loadMaxIssuedBooks(library);

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

        HashMap<MemberType, Integer> maxIssueDays = library.getMaxIssueDays();
        HashMap<MemberType, Integer> maxIssuedBooks = library.getMaxIssuedBooks();

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    //TODO kad Bubi doda cenovnik ovde da idu cene
                    MemberType memberType = MemberType.valueOf(group.getSelectedToggle().toString());

                    price6months.setText(group.getSelectedToggle().toString());
                    price12months.setText(group.getSelectedToggle().toString());

                    maxNumberOfBooks.setText("Number of books you can issue is " + maxIssuedBooks.get(memberType) + ".");
                    maxNumberOfDays.setText("Number of days you can keep your books is " + maxIssueDays.get(memberType) + ".");
                }
            }
        });

    }


}
