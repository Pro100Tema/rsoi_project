package com.example.gateway;

public class PaymentCarInfo {

    private String user_uid;

    private Integer price;

    private String payment_uid;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(String payment_uid) {
        this.payment_uid = payment_uid;
    }
}
