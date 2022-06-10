package com.example.front.dto;

import java.util.UUID;

public class CarAndPaymentInfo {
    private UUID carUid;
    private UUID paymentUid;

    public CarAndPaymentInfo() {
    }

    public CarAndPaymentInfo(UUID carUid, UUID paymentUid) {
        this.carUid = carUid;
        this.paymentUid = paymentUid;
    }

    public UUID getCarUid() {
        return carUid;
    }

    public void setCarUid(UUID carUid) {
        this.carUid = carUid;
    }

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public void setPaymentUid(UUID paymentUid) {
        this.paymentUid = paymentUid;
    }

    @Override
    public String toString() {
        return "CarAndPaymentInfo{" + "carUid=" + carUid + ", paymentUid=" + paymentUid + "}";
    }
}
