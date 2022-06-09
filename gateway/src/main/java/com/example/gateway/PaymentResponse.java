package com.example.gateway;

import java.util.List;

public class PaymentResponse {

    private List<String> payment_uid;

    public List<String> getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(List<String> payment_uid) {
        this.payment_uid = payment_uid;
    }
}
