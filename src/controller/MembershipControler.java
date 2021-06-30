package controller;

import model.Library;
import model.Member;
import model.Payment;
import model.enums.MemberType;
import utils.exceptions.InvalidTransactionException;

import java.time.LocalDate;

public class MembershipControler {

    private Library library;

    public MembershipControler(Library library){ this.library = library; }

    public void payMembership(Member m, int numOfMonths) throws InvalidTransactionException {
        MemberType  type = m.getType();
        double price = library.getCurrentCatalog().getPrice(type, numOfMonths);

        if (contactBankingSystem()) {
            createPayment(m, numOfMonths);
            m.extendMembership();
        } else {
            throw new InvalidTransactionException();
        }
    }

    private void createPayment(Member m, int numOfMonths){
        //TODO nextId value
        int nextId = 0;
        Payment newPayment = new Payment(nextId, LocalDate.now().plusMonths(numOfMonths), m);
        m.addPayment(newPayment);
    }

    private boolean contactBankingSystem() {
        return true;
    }
}
