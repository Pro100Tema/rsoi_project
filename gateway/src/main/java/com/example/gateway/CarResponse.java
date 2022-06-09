package com.example.gateway;

import java.util.UUID;

public class CarResponse {
    private Long id;
    private String car_uid;
    private String brand;
    private String model;
    private String registration_number;
    private Integer power;
    private Integer price;
    private String type;
    private Boolean availability;

    public CarResponse(Long id, String car_uid, String brand, String model, String registration_number, Integer power, Integer price, String type, Boolean availability) {
        this.id = id;
        this.car_uid = car_uid;
        this.brand = brand;
        this.model = model;
        this.registration_number = registration_number;
        this.power = power;
        this.price = price;
        this.type = type;
        this.availability = availability;
    }

    public CarResponse() {
    }


    public String getCarUid() {
        return car_uid;
    }

    public void setCarUid(String carUid) {
        this.car_uid = carUid;
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
        return registration_number;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registration_number = registrationNumber;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAvailable() {
        return availability;
    }

    public void setAvailable(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "CarResponse{" + "carUid=" + car_uid + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", registrationNumber='" + registration_number + '\'' + ", power=" + power + ", price=" + price + ", type=" + type + ", availability=" + availability + "}";
    }
}
