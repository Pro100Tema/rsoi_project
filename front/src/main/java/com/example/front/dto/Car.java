package com.example.front.dto;

import java.util.UUID;

public class Car {
    private Long id;

    private UUID car_uid;

    private String brand;

    private String model;

    private String registration_number;

    private Integer power;

    private Integer price;

    private CarListClass type;

    private Boolean availability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCar_uid() {
        return car_uid;
    }

    public void setCar_uid(UUID car_uid) {
        this.car_uid = car_uid;
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

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
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

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", car_uid=" + car_uid + ", brand='" + brand + '\'' + ", model='" + model + '\'' + ", registration_number='" + registration_number + '\'' + ", power=" + power + ", price=" + price + ", type=" + type + ", availability=" + availability + "}";
    }
}
