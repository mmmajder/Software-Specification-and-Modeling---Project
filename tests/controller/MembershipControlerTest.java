package controller;

import model.Library;
import model.Member;
import model.PriceCatalog;
import model.enums.MemberType;
import org.junit.jupiter.api.Test;
import utils.exceptions.InvalidTransactionException;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MembershipControlerTest {

    @Test
    void payMembership6() throws InvalidTransactionException {
        Library library = getLibrary();
        MembershipControler membershipControler = new MembershipControler(library);
        Member member = new Member();
        member.setType(MemberType.STUDENT);

        membershipControler.payMembership(member, 6);
        assertEquals(member.getMembershipExpirationDate(), LocalDate.now().plusMonths(6));
    }

    @Test
    void payMembership12() throws InvalidTransactionException {
        Library library = getLibrary();
        MembershipControler membershipControler = new MembershipControler(library);
        Member member = new Member();
        member.setType(MemberType.STUDENT);

        membershipControler.payMembership(member, 12);
        assertEquals(member.getMembershipExpirationDate(), LocalDate.now().plusMonths(12));
    }

    private Library getLibrary(){
        Library library = new Library();
        HashMap<MemberType, Double> prices = getTestPrices();
        PriceCatalog priceCatalog = new PriceCatalog();
        priceCatalog.setHalfAYearPrices(prices);
        priceCatalog.setFullYearPrices(prices);
        library.setNewPriceCatalog(priceCatalog);

        return library;
    }

    private HashMap<MemberType, Double> getTestPrices(){
        HashMap <MemberType, Double> prices = new HashMap<>();
        prices.put(MemberType.REGULAR, 100.0);
        prices.put(MemberType.STUDENT, 100.0);
        prices.put(MemberType.PRESCHOOLER, 100.0);
        prices.put(MemberType.PUPIL, 100.0);
        prices.put(MemberType.RETIRED, 100.0);

        return prices;
    }
}