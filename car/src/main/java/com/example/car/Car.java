package com.example.car;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "car", schema = "public")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(name = "car_uid",nullable = false, unique = true)
    private UUID car_uid;

    @Column(name = "brand", nullable = false, length = 80)
    private String brand;

    @Column(name = "model",nullable = false, length = 80)
    private String model;

    @Column(name = "registration_number",nullable = false, length = 20)
    private String registration_number;

    @Column(name = "power",nullable = false, length = 80)
    private Integer power;

    @Column(name = "price",nullable = false)
    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",length = 20)
    private CarListClass type;

    @Column(name = "availability",nullable = false)
    private Boolean availability;

    public Car() {
    }

    public Car(Long id, UUID car_uid, String brand, String model, String registration_number, Integer power, Integer price, CarListClass type, Boolean availability) {
        super();
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

    public Car(UUID car_uid, String brand, String model, String registration_number, Integer power, Integer price, CarListClass type, Boolean availability) {
        super();
        this.car_uid = car_uid;
        this.brand = brand;
        this.model = model;
        this.registration_number = registration_number;
        this.power = power;
        this.price = price;
        this.type = type;
        this.availability = availability;
    }

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
