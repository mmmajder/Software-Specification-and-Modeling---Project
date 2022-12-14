package controller;

import model.Library;
import model.Member;
import model.Payment;
import model.PendingReservation;
import model.enums.MemberType;
import utils.exceptions.InvalidTransactionException;

import java.time.LocalDate;
import java.util.List;

public class MembershipControler {

    private Library library;

    public MembershipControler(Library library) {
        this.library = library;
    }

    public void payMembership(Member m, int numOfMonths) throws InvalidTransactionException {
        if (contactBankingSystem()) {
            createPayment(m, numOfMonths);
            m.extendMembership();
            library.notifyObservers();
        } else {
            throw new InvalidTransactionException();
        }
    }

    private void createPayment(Member m, int numOfMonths){
        int nextId = getNextId();
        LocalDate fromDate = calculateFromDate(m);
        Payment newPayment = new Payment(nextId, LocalDate.now(), fromDate.plusMonths(numOfMonths), m, numOfMonths);
        m.addPayment(newPayment);
        library.addPayment(newPayment);
    }

    private int getNextId(){
        List<Payment> payments = library.getPayments();
        Integer maxId = payments.stream()
                .map(Payment::getPaymentId)
                .max(Integer::compare).orElse(1);

        return ++maxId;
    }

    private LocalDate calculateFromDate(Member member) {
        LocalDate fromDate;

        if (isPayingFirstTime(member)) {
            fromDate = LocalDate.now();
        } else {
            Payment lastPayment = member.getLastPayment();

            if (lastPayment.getValidToDate().isBefore(LocalDate.now())) {
                fromDate = LocalDate.now();
            }
            // if member paid membership before it was cancelled, his membership is prolonged
            // as if he paid it on the last day of his current membership
            else {
                fromDate = lastPayment.getValidToDate();
            }
        }

        return fromDate;
    }

    private boolean isPayingFirstTime(Member member) {
        return member.getPayments().size() == 0;
    }

    private boolean contactBankingSystem() {
        return true;
    }
}
