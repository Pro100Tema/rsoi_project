package com.example.front.dto;

import com.vaadin.flow.component.datepicker.DatePicker;

import java.util.Date;
import java.util.UUID;

public class Rental {
    private Long id;

    private UUID rental_uid;

    private String username;

    private UUID payment_uid;

    private UUID car_uid;

    private DatePicker date_from;

    private DatePicker date_to;

    private RentalStatus status;

    public Rental() {
    }

    public Rental(Long id, UUID rental_uid, String username, UUID payment_uid, UUID car_uid, DatePicker date_from, DatePicker date_to, RentalStatus status) {
        super();
        this.id = id;
        this.rental_uid = rental_uid;
        this.username = username;
        this.payment_uid = payment_uid;
        this.car_uid = car_uid;
        this.date_from = date_from;
        this.date_to = date_to;
        this.status = status;
    }

    public Rental(UUID rental_uid, String username, UUID payment_uid, UUID car_uid, DatePicker date_from, DatePicker date_to, RentalStatus status) {
        super();
        this.rental_uid = rental_uid;
        this.username = username;
        this.payment_uid = payment_uid;
        this.car_uid = car_uid;
        this.date_from = date_from;
        this.date_to = date_to;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRental_uid() {
        return rental_uid;
    }

    public void setRental_uid(UUID rental_uid) {
        this.rental_uid = rental_uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getPayment_uid() {
        return payment_uid;
    }

    public void setPayment_uid(UUID payment_uid) {
        this.payment_uid = payment_uid;
    }

    public UUID getCar_uid() {
        return car_uid;
    }

    public void setCar_uid(UUID car_uid) {
        this.car_uid = car_uid;
    }

    public DatePicker getDate_from() {
        return date_from;
    }

    public void setDate_from(DatePicker date_from) {
        this.date_from = date_from;
    }

    public static String getString(Date dateFrom) {
        int month = dateFrom.getMonth();
        int date = dateFrom.getDate();
        String year = (dateFrom.getYear() + 1900) + "-";
        if ((month + 1) > 9) {
            year += month + 1;
        } else {
            year += "0" + (month + 1);
        }
        year += "-";
        if (date > 9) {
            year += date;
        } else {
            year += "0" + date;
        }
        return year;
    }

    public DatePicker getDate_to() {
        return date_to;
    }

    public void setDate_to(DatePicker date_to) {
        this.date_to = date_to;
    }

    //public String getDate_from_string() {
    //    return getString((date_to));
    //}

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    /*public String getDate_to_string() {
        return getString((date_to));
    }*/

    @Override
    public String toString() {
        return "Rental{" + "id=" + id + ", rental_uid=" + rental_uid + ", username='" + username + '\'' + ", payment_uid=" + payment_uid + ", car_uid=" + car_uid + ", date_from=" + date_from + ", date_to=" + date_to + ", status=" + status + "}";
    }
}