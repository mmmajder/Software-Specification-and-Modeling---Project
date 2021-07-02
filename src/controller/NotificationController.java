package controller;

import model.Library;
import model.Member;
import model.Notification;
import utils.exceptions.MissingValueException;

public class NotificationController {

    private Library library;

    public NotificationController(Library library){ this.library = library; }

    public void addNotification(Member member, Notification notification) throws MissingValueException {
        if (notification == null){ throw new MissingValueException("notification"); }
        member.addNotification(notification);
    }
}
