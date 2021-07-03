package model;

import java.time.LocalDate;

public class Payment {
    private int paymentId;
    private LocalDate paymentDate;
    private LocalDate validToDate;
    private Member member;
    private int numOfMonths;

    public Payment(int paymentId, LocalDate paymentDate, LocalDate validToDate, Member member, int numOfMonths) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.validToDate = validToDate;
        this.member = member;
        this.numOfMonths = numOfMonths;
    }

    public LocalDate getValidToDate() { return validToDate; }

    public int getPaymentId() { return paymentId; }

    public LocalDate getPaymentDate() { return paymentDate; }

    public Member getMember() { return member; }

    public int getNumOfMonths() { return numOfMonths; }
}
