package com.example.gateway;

import java.util.UUID;

public class Payment {
    private Long id;

    private UUID payment_uid;
    private PaymentStatus status;
    private Integer price;

    public Payment() {
    }

    public Payment(Long id, UUID payment_uid, PaymentStatus status, Integer price) {
        super();
        this.id = id;
        this.payment_uid = payment_uid;
        this.status = status;
        this.price = price;
    }

    public Payment(UUID payment_uid, PaymentStatus status, Integer price) {
        super();
        this.payment_uid = payment_uid;
        this.status = status;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(UUID payment_uid) {
        this.payment_uid = payment_uid;
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
        return "Payment{" + "id=" + id + ", payment_uid=" + payment_uid + ", status=" + status + ", price=" + price + "}";
    }
}
