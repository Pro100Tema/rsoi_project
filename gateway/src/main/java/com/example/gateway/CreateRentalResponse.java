package com.example.gateway;

import java.util.UUID;

public class CreateRentalResponse {
    private UUID rentalUid;
    private UUID carUid;
    private String dateFrom;
    private String dateTo;
    private PaymentInfo payment;
    private RentalStatus status;

    public CreateRentalResponse() {
    }

    public CreateRentalResponse(UUID rentalUid, UUID carUid, String dateFrom, String dateTo, PaymentInfo payment, RentalStatus status) {
        this.rentalUid = rentalUid;
        this.carUid = carUid;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.payment = payment;
        this.status = status;
    }

    public UUID getRentalUid() {
        return rentalUid;
    }

    public void setRentalUid(UUID rentalUid) {
        this.rentalUid = rentalUid;
    }

    public UUID getCarUid() {
        return carUid;
    }

    public void setCarUid(UUID carUid) {
        this.carUid = carUid;
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

    public PaymentInfo getPayment() {
        return payment;
    }

    public void setPayment(PaymentInfo payment) {
        this.payment = payment;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CreateRentalResponse{" + "rental_uid=" + rentalUid + ", car_uid=" + carUid + ", date_from=" + dateFrom + ", date_to=" + dateTo + ", payment=" + payment + ", status=" + status + "}";
    }
}
