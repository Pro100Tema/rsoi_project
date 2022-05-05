package com.example.gateway.queue;

import java.util.UUID;

public class QueueRequest {
    private RequestType requestType = null;
    private String username = null;
    private UUID rentalUid = null;
    private UUID paymentUid = null;
    private UUID carUid = null;
    private Boolean availability = null;

    public RequestType getRequestType() {
        return requestType;
    }

    public String getUsername() {
        return username;
    }

    public UUID getRentalUid() {
        return rentalUid;
    }

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public UUID getCarUid() {
        return carUid;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setCancelUserRental(String username, UUID rentalUid) {
        this.requestType = RequestType.CANCEL_USER_RENTAL;
        this.username = username;
        this.rentalUid = rentalUid;
    }

    public void setFinishUserRental(String username, UUID rentalUid) {
        this.requestType = RequestType.FINISH_USER_RENTAL;
        this.username = username;
        this.rentalUid = rentalUid;
    }

    public void setUpdateCarReserve(UUID carUid, Boolean availability) {
        this.requestType = RequestType.UPDATE_CAR_RESERVE;
        this.carUid = carUid;
        this.availability = availability;
    }

    public void setCancelPayment(UUID paymentUid) {
        this.requestType = RequestType.CANCEL_PAYMENT;
        this.paymentUid = paymentUid;
    }

    @Override
    public String toString() {
        return "QueueRequest{" + "requestType=" + requestType + ", username=" + username + ", rentalUid=" + rentalUid + ", paymentUid=" + paymentUid + ", carUid=" + carUid + ", availability=" + availability + "}";
    }

}


