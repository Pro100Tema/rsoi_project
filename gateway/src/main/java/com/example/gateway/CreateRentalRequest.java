package com.example.gateway;

import java.util.Date;
import java.util.UUID;

public class CreateRentalRequest {
    private UUID carUid;
    private Date dateFrom;
    private Date dateTo;

    public CreateRentalRequest() {

    }

    public CreateRentalRequest(UUID carUid, Date dateFrom, Date dateTo) {
        this.carUid = carUid;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public static String getString(Date dateFrom) {
        int month = dateFrom.getMonth();
        int date = dateFrom.getDate();
        String full_date = (dateFrom.getYear() + 1900) + "-";
        if ((month + 1) > 9) {
            full_date += month + 1;
        } else {
            full_date += "0" + (month + 1) + "-";
        }
        full_date += "-";
        if (date > 9) {
            full_date += date;
        } else {
            full_date += "0" + date;
        }
        return full_date;
    }

    public UUID getCarUid() {
        return carUid;
    }

    public void setCarUid(UUID carUid) {
        this.carUid = carUid;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateFromString() {
        return getString(dateFrom);
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateToString() {
        return getString(dateTo);
    }

    @Override
    public String toString() {
        return "CreateRentalRequest{" + "carUid=" + carUid + ", dateFrom=" + dateFrom + ", date_to=" + dateTo + "}";
    }
}

