package com.example.payment;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "payments", schema = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(nullable = false)
    private UUID payment_uid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @Column(nullable = false)
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
