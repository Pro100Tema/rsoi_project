package com.example.front.dto;

import java.util.List;

public class CarFullInfo {
    private Integer fullElements;
    private List<Car> cars;

    public Integer getFullElements() {
        return fullElements;
    }

    public void setFullElements(Integer fullElements) {
        this.fullElements = fullElements;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "CarFullInfo{" + "fullElements=" + fullElements + ", cars=" + cars + "}";
    }
}

