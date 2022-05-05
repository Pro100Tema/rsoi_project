package com.example.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public ResponseEntity<Car> getCar(UUID carUid) {
        //System.out.println("+++");
        try {
            //System.out.println("getCar");
            Car car = carRepository.findByCar_uid(carUid);
            return new ResponseEntity<>(car, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CarFullInfo> getCars(Integer page, Integer size, Boolean showAll) {
        //System.out.println("getCars");
        ArrayList<Car> cars = new ArrayList<>(carRepository.findAll());
        //System.out.println(cars);
        ArrayList<Car> availableCars = new ArrayList<>();

        if (showAll) {
            availableCars = cars;
        } else {
            for (Car car : cars) {
                //System.out.println(car.getAvailability());
                if (car.getAvailability())
                    availableCars.add(car);
                //System.out.println(availableCars);
            }
        }

        ArrayList<Car> chosenCars = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if ((page - 1) * size + i < availableCars.size()) {
                //System.out.println("ChosenCars");
                chosenCars.add(availableCars.get(i));
                //System.out.println(chosenCars);
            }
        }

        return new ResponseEntity<>(new CarFullInfo(availableCars.size(), chosenCars), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> updateCar(UUID carUid, Boolean availability) {
        //System.out.println("+++++");
        try {
            //System.out.println("updateCar");
            Car car = carRepository.findByCar_uid(carUid);
            car.setAvailability(availability);
            carRepository.save(car);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}