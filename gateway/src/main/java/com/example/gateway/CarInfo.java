package com.example.gateway;

import java.util.UUID;

public class CarInfo {
    private UUID carUid;
    private String brand;
    private String model;
    private String registrationNumber;

    public CarInfo() {
    }

    public CarInfo(UUID carUid, String brand, String model, String registrationNumber) {
        this.carUid = carUid;
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
    }

    public UUID getCarUid() {
        return carUid;
    }

    public void setCarUid(UUID carUid) {
        this.carUid = carUid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "CarInfo{" + "carUid=" + carUid + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", registrationNumber='" + registrationNumber + '\'' + "}";
    }
}
