package model;

import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private LocalDate paymentDate;
    private LocalDate validToDate;
    private Member member;

    public Payment(int paymentId, LocalDate paymentDate, LocalDate validToDate, Member member){
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.validToDate = validToDate;
        this.member = member;
    }

    public LocalDate getValidToDate() { return validToDate; }
}
