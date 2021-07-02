package controller;

import model.Library;
import model.Notification;
import model.ReservedBook;
import model.enums.SampleState;

import java.util.ArrayList;
import java.util.List;

public class SystemClock {

    private Library library;
    private NotificationController notificationController;

    public SystemClock(Library library) {
        this.library = library;
        this.notificationController = new NotificationController(library); // used to send notifications to members
    }

    public void runDaily(){
        removeExpiredReservations();
    }

    public void removeExpiredReservations(){
        List<Integer> indexesToRemove = getIndexesToRemove();
        removeExpiredReservations(indexesToRemove);
    }

    private List<Integer> getIndexesToRemove(){
        List<Integer> indexesToRemove = new ArrayList<>();

        for (int i = 0; i < library.getReservedBooks().size(); i++){
            // or < 0, depending if it will be run in the evening(after the work day) or tomorrow morning (before the work day)
            // == is for evening
            if (library.getReservedBooks().get(i).getDaysLeft() == 0){
                indexesToRemove.add(i);
            }
        }

        return indexesToRemove;
    }

    private void removeExpiredReservations(List<Integer> indexesToRemove){
        int alreadyRemoved = 0;

        for (Integer i : indexesToRemove){
            int indexToRemove = i-alreadyRemoved; // list shrinks every time an element is removed
            ReservedBook reservedBook = library.getReservedBooks().get(indexToRemove);
            removeExpiredReservation(reservedBook, indexToRemove);
            alreadyRemoved++;
        }
    }

    private void removeExpiredReservation(ReservedBook reservedBook, int indexToRemove){
        notificationController.reservationExpired(reservedBook);
        reservedBook.getMember().removeReservedBook();
        reservedBook.getBook().makeAvailable();
        library.getReservedBooks().remove(indexToRemove);
    }

}
