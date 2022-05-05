package com.example.gateway;


import java.util.UUID;

public class PaymentInfo {
    private UUID paymentUid;
    private PaymentStatus status;
    private Integer price;

    public PaymentInfo(UUID paymentUid, PaymentStatus status, Integer price) {
        this.paymentUid = paymentUid;
        this.status = status;
        this.price = price;
    }

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public void setPaymentUid(UUID paymentUid) {
        this.paymentUid = paymentUid;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" + "payment_uid=" + paymentUid + ", status=" + status + ", price=" + price + "}";
    }
}
