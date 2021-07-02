package view.member;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.*;
import observer.Observer;
import view.member.model.NotificationTable;

import java.io.IOException;

public class NotificationsController implements Observer {

    public TableView<NotificationTable> notificationTable;
    ObservableList<NotificationTable> dataNotificationTable;
    Library library;
    Account account;
    ILibraryRepo libraryRepo;

    public void initData(Account account) throws IOException {
        this.account = account;
        this.library = new Library();
        libraryRepo = new LibraryRepo();
        libraryRepo.loadNotifications(library);
        notificationTable.getColumns().add(new TableColumn("Notification") {
            {
                prefWidthProperty().bind(notificationTable.widthProperty().multiply(0.7));
            }
        });
        notificationTable.getColumns().add(new TableColumn("Date") {
            {
                prefWidthProperty().bind(notificationTable.widthProperty().multiply(0.3));
            }
        });
    }

    private ObservableList<NotificationTable> getNotifications() {
        ObservableList<NotificationTable> list = FXCollections.observableArrayList();
//        for (Notification notification : library.getNotification(account)) {
//            list.add(new NotificationTable(notification.getMessage(), notification.getDate()));
//        }
        return list;
    }

    @Override
    public void updatePerformed() {
        libraryRepo.loadNotifications(library);
        notificationTable.setItems(getNotifications());
    }
}