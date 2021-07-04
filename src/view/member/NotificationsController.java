package view.member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import observer.Observer;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import view.member.model.NotificationTable;

public class NotificationsController implements Observer {

    public TableView<NotificationTable> notificationTable;
    ObservableList<NotificationTable> dataNotificationTable;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    public void initData(Account account) {
        this.account = account;
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        library.addObserver(this);
        libraryRepo.loadAccounts(library);
        libraryRepo.loadNotifications(library);
        TableColumn colNotification = new TableColumn("Notification") {
            {
                prefWidthProperty().bind(notificationTable.widthProperty().multiply(0.7));
            }
        };
        notificationTable.getColumns().add(colNotification);
        TableColumn colDate = new TableColumn("Date") {
            {
                prefWidthProperty().bind(notificationTable.widthProperty().multiply(0.3));
            }
        };
        notificationTable.getColumns().add(colDate);

        colNotification.setCellValueFactory(new PropertyValueFactory<NotificationTable, String>("notification"));
        colDate.setCellValueFactory(new PropertyValueFactory<NotificationTable, String>("date"));

        notificationTable.setItems(getNotifications());
    }

    private ObservableList<NotificationTable> getNotifications() {
        ObservableList<NotificationTable> list = FXCollections.observableArrayList();
        for (Notification notification : account.getNotifications()) {
            list.add(new NotificationTable(notification.getMessage(), notification.getDate()));
        }
        return list;
    }

    @Override
    public void updatePerformed() {
        notificationTable.setItems(getNotifications());
    }
}