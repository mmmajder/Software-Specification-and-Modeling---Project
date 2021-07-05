package controller;

import model.Edition;
import model.Library;
import model.Member;
import model.PendingReservation;
import repository.ILibraryRepo;
import repository.LibraryRepo;
import utils.exceptions.MemberAlreadyHasPendingRequestException;
import utils.exceptions.MemberAlreadyHasReservedBook;

import java.util.List;

public class MemberReservationController {
    private Library library;
    private ILibraryRepo libraryRepo;

    public MemberReservationController(Library library) {
        this.library = library;
        this.libraryRepo = new LibraryRepo();
    }

    public void sendReservationRequest(Member member, Edition edition) throws MemberAlreadyHasPendingRequestException,
            MemberAlreadyHasReservedBook {

        validateMembersReservationAbility(member);
        PendingReservation pr = createPendingReservation(member, edition);
        member.setPendingReservation(pr);
        library.addPendingReservation(pr);
        libraryRepo.addMembersPendingReservation(member, pr);
        libraryRepo.addPendingReservation(pr);
    }

    private void validateMembersReservationAbility(Member member) throws MemberAlreadyHasPendingRequestException, MemberAlreadyHasReservedBook {
        if (member.getPendingReservation() != null) {
            throw new MemberAlreadyHasPendingRequestException();
        }
        if (member.getReservation() != null) {
            throw new MemberAlreadyHasReservedBook();
        }
    }

    private PendingReservation createPendingReservation(Member member, Edition edition){
        int id = getNextId();

        return new PendingReservation(id, member, edition);
    }

    private int getNextId(){
        List<PendingReservation> pendingReservations = library.getPendingReservations();
        Integer maxId = pendingReservations.stream()
                .map(PendingReservation::getId)
                .max(Integer::compare).orElse(1);

        return ++maxId;
    }

}
