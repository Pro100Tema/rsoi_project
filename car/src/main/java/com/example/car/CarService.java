package com.example.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public ResponseEntity<GetCarFullInfo> getCarFullInfo(UUID carUid) {
        //System.out.println("+++");
        try {
            //System.out.println("getCar");
            Car car = carRepository.findByCar_uid(carUid);
            GetCarFullInfo carResponse = new GetCarFullInfo();
            carResponse.setCar_uid(car.getCar_uid().toString());
            carResponse.setBrand(car.getBrand());
            carResponse.setModel(car.getModel());
            carResponse.setRegistration_number(car.getRegistration_number());
            carResponse.setPower(car.getPower());
            carResponse.setType(String.valueOf(car.getType()));
            carResponse.setPrice(car.getPrice());
            carResponse.setAvailability(car.getAvailability());
            return new ResponseEntity<>(carResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<CarResponse> getCar(UUID carUid) {
        //System.out.println("+++");
        try {
            //System.out.println("getCar");
            Car car = carRepository.findByCar_uid(carUid);
            CarResponse carResponse = new CarResponse();
            carResponse.setId(car.getId());
            carResponse.setCar_uid(car.getCar_uid().toString());
            carResponse.setBrand(car.getBrand());
            carResponse.setModel(car.getModel());
            carResponse.setRegistration_number(car.getRegistration_number());
            carResponse.setPower(car.getPower());
            carResponse.setType(String.valueOf(car.getType()));
            carResponse.setPrice(car.getPrice());
            carResponse.setAvailability(car.getAvailability());
            return new ResponseEntity<>(carResponse, HttpStatus.OK);
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
                if (car.getAvailability()) {
                    availableCars.add(car);
                }
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

    public ResponseEntity<GetCarFullInfo> createCar(GetCarFullInfo car){
        Car newCar = new Car();
        newCar.setBrand(car.getBrand());
        newCar.setType(CarListClass.valueOf(car.getType()));
        newCar.setModel(car.getModel());
        newCar.setPower(car.getPower());
        newCar.setPrice(car.getPrice());
        newCar.setRegistration_number(car.getRegistration_number());
        UUID car_uuid = UUID.randomUUID();
        newCar.setCar_uid(car_uuid);
        newCar.setAvailability(true);
        Random rand = new Random();
        int maxNumber = 100;
        int randomNumber = rand.nextInt(maxNumber) + 1;
        newCar.setId(3L);
        carRepository.save(newCar);
        car.setCar_uid(car_uuid.toString());
        car.setAvailability(true);
        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    public ResponseEntity<GetCarFullInfo> changeAvailability(GetCarFullInfo carResponse) {
        Car car = carRepository.findByCar_uid(UUID.fromString(carResponse.getCar_uid()));
        car.setAvailability(carResponse.getAvailability());
        carRepository.save(car);
        return new ResponseEntity<>(carResponse, HttpStatus.OK);
    }

    public ResponseEntity<GetCarFullInfo> deleteCar(String car_uid) {
        Car car = carRepository.findByCar_uid(UUID.fromString(car_uid));
        carRepository.delete(car);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}