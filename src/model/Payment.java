package model;

import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private LocalDate validToDate;
    private Member member;

    public Payment(int paymentId, LocalDate validToDate, Member member){
        this.paymentId = paymentId;
        this.validToDate = validToDate;
        this.member = member;
    }

    public LocalDate getValidToDate() { return validToDate; }
}
