package controller;

import model.IssuedBook;
import model.Library;
import model.Reservation;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SystemClock {

    private Library library;
    private NotificationController notificationController;
    private IssuedBookController issuedBookController;

    //TODO move these constants somewhere
    private final int DAYS_UNTIL_RETURN_DATE_TO_NOTIFY = 3;
    private final int DAYS_AFTER_RETURN_DATE_TO_SEND_FINE = 0; // if not 0, put in negative

    public SystemClock(Library library) {
        this.library = library;
        this.notificationController = new NotificationController(library); // used to send notifications to members
        this.issuedBookController = new IssuedBookController(library);
    }

    public void runDaily(){
        removeExpiredReservations();
        checkIssuedBooks();
    }

    public void checkIssuedBooks(){
        for (IssuedBook issuedBook : library.getCurrentlyIssued()){
            int daysToReturnDate = calculateDaysToReturnDate(issuedBook);

            if (daysToReturnDate == DAYS_UNTIL_RETURN_DATE_TO_NOTIFY){
                notificationController.reminderToReturnBook(issuedBook);
            }else if (daysToReturnDate <= DAYS_AFTER_RETURN_DATE_TO_SEND_FINE){
                notificationController.sendFine(issuedBook);
            }
        }
    }

    private int calculateDaysToReturnDate(IssuedBook issuedBook){
        LocalDate calculatedReturnDate = issuedBookController.calculateReturnDate(issuedBook);
        return (int) Duration.between(LocalDate.now(), calculatedReturnDate).toDays();
    }

    public void removeExpiredReservations(){
        List<Integer> indexesToRemove = getIndexesToRemove();
        removeExpiredReservations(indexesToRemove);
    }

    private List<Integer> getIndexesToRemove(){
        List<Integer> indexesToRemove = new ArrayList<>();

        for (int i = 0; i < library.getReservations().size(); i++){
            // or < 0, depending if it will be run in the evening(after the work day) or tomorrow morning (before the work day)
            // == is for evening
            if (library.getReservations().get(i).getDaysLeft() == 0){
                indexesToRemove.add(i);
            }
        }

        return indexesToRemove;
    }

    private void removeExpiredReservations(List<Integer> indexesToRemove){
        int alreadyRemoved = 0;

        for (Integer i : indexesToRemove){
            int indexToRemove = i-alreadyRemoved; // list shrinks every time an element is removed
            Reservation reservation = library.getReservations().get(indexToRemove);
            removeExpiredReservation(reservation, indexToRemove);
            alreadyRemoved++;
        }
    }

    private void removeExpiredReservation(Reservation reservation, int indexToRemove){
        notificationController.reservationExpired(reservation);
        reservation.getMember().removeReservation();
        reservation.getBook().makeAvailable();
        library.getReservations().remove(indexToRemove);
    }



}
