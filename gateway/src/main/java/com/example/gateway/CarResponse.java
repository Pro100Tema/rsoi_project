package com.example.gateway;

import java.util.UUID;

public class CarResponse {
    private UUID carUid;
    private String brand;
    private String model;
    private String registrationNumber;
    private Integer power;
    private Integer price;
    private CarListClass type;
    private Boolean available;

    public CarResponse(UUID carUid, String brand, String model, String registrationNumber, Integer power, Integer price, CarListClass type, Boolean available) {
        this.carUid = carUid;
        this.brand = brand;
        this.model = model;
        this.registrationNumber = registrationNumber;
        this.power = power;
        this.price = price;
        this.type = type;
        this.available = available;
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

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public CarListClass getType() {
        return type;
    }

    public void setType(CarListClass type) {
        this.type = type;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "CarResponse{" + "carUid=" + carUid + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", registrationNumber='" + registrationNumber + '\'' + ", power=" + power + ", price=" + price + ", type=" + type + ", availability=" + available + "}";
    }
}
