package com.example.gateway;

import java.util.List;

public class CarFullInfo {
    private Integer totalElements;
    private List<Car> cars;

    public CarFullInfo() {
    }

    public CarFullInfo(Integer totalElements, List<Car> cars) {
        this.totalElements = totalElements;
        this.cars = cars;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }


    @Override
    public String toString() {
        return "CarFullInfo{" + "totalElements=" + totalElements + ", cars=" + cars + "}";
    }
}
