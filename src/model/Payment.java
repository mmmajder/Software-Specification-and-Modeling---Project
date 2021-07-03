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

    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public LocalDate getPaymentDate() { return paymentDate; }

    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public void setValidToDate(LocalDate validToDate) { this.validToDate = validToDate; }

    public Member getMember() { return member; }

    public void setMember(Member member) { this.member = member; }
}
