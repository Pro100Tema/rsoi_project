package com.example.gateway;

import java.util.UUID;

public class RentalResponse {
    private UUID rentalUid;
    private String dateFrom;
    private String dateTo;
    private RentalStatus status;
    private CarInfo car;
    private PaymentInfo payment;

    public RentalResponse(UUID rentalUid, String dateFrom, String dateTo, RentalStatus status, CarInfo car, PaymentInfo payment) {
        this.rentalUid = rentalUid;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
        this.car = car;
        this.payment = payment;
    }

    public UUID getRentalUid() {
        return rentalUid;
    }

    public void setRentalUid(UUID rentalUid) {
        this.rentalUid = rentalUid;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public CarInfo getCar() {
        return car;
    }

    public void setCar(CarInfo car) {
        this.car = car;
    }

    public PaymentInfo getPayment() {
        return payment;
    }

    public void setPayment(PaymentInfo payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "RentalResponse{" + "rentalUid=" + rentalUid + ", dateFrom=" + dateFrom + ", dateTo=" + dateTo + ", status=" + status + ", car=" + car + ", payment=" + payment + "}";
    }
}
