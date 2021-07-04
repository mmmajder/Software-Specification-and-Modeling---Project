package controller;

import model.*;
import utils.exceptions.NoSuchPendingRequestException;

public class LibrarianReservationController {

    private Library library;

    public LibrarianReservationController(Library library) {
        this.library = library;
    }

    public void acceptReservation(PendingReservation pr, Book book) throws NoSuchPendingRequestException {
        Reservation rb = createReservation(pr.getMember(), book);
        setReservation(pr.getMember(), rb);
        removePendingReservation(pr.getMember(), pr.getId());
        //TODO notify the member
    }

    private Reservation createReservation(Member member, Book book) {
        //TODO new id value
        int id = 0;
        return new Reservation(id, member, book);
    }

    public void declineReservation(PendingReservation pr) throws NoSuchPendingRequestException {
        removePendingReservation(pr.getMember(), pr.getId());
        //TODO notify the member
    }

    private void setReservation(Member member, Reservation reservation) {
        member.setReservation(reservation);
        library.addReservation(reservation);
    }

    private void removePendingReservation(Member member, int prId) throws NoSuchPendingRequestException {
        member.setPendingReservation(null);
        removePendingReservation(prId);
    }

    private void removePendingReservation(int prId) throws NoSuchPendingRequestException {

        boolean pendingReservationExists = false;

        for (PendingReservation pr : library.getPendingReservations()) {

            if (pr.getId() == prId) {

                library.removePendingReservation(pr);
                pendingReservationExists = true;
                break;
            }
        }

        if (!pendingReservationExists) {

            throw new NoSuchPendingRequestException();
        }

    }

}
