package com.example.gateway;

import java.time.LocalDateTime;

public class RentalInfo {
    private String rental_uid;
    private LocalDateTime dateFrom;
    private LocalDateTime  dateTo;
    private String car_uid;
    private String user_uid;
    private String payment_uid;

    public String getRental_uid() {
        return rental_uid;
    }

    public void setRental_uid(String rental_uid) {
        this.rental_uid = rental_uid;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public String getCar_uid() {
        return car_uid;
    }

    public void setCar_uid(String car_uid) {
        this.car_uid = car_uid;
    }

    public String getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(String payment_uid) {
        this.payment_uid = payment_uid;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }
}
