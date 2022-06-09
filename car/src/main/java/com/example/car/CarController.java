package com.example.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin("https://localhost:8001/api/v1")
@RequestMapping("api/v1")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/car/{carUid}")
    public ResponseEntity<CarResponse> getCar(@PathVariable("carUid") UUID carUid) {
        return carService.getCar(carUid);
    }

    @GetMapping("/car-info/{carUid}")
    public ResponseEntity<GetCarFullInfo> getCarFullInfo(@PathVariable("carUid") UUID carUid) {
        return carService.getCarFullInfo(carUid);
    }

    @GetMapping("/cars")
    public ResponseEntity<CarFullInfo> getCars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) Boolean showAll) {
        return carService.getCars(page, size, showAll);
    }

    @PatchMapping("/cars/{carUid}")
    public ResponseEntity<HttpStatus> updateCar(@PathVariable("carUid") UUID carUid, @RequestParam Boolean availability) {
        return carService.updateCar(carUid, availability);
    }

    @PostMapping("/new-car")
    public ResponseEntity<GetCarFullInfo> createCar(@RequestBody GetCarFullInfo car) {
        return carService.createCar(car);
    }

    @PostMapping("/availability")
    public ResponseEntity<GetCarFullInfo> changeAvailability(@RequestBody GetCarFullInfo carResponse) {
        return carService.changeAvailability(carResponse);
    }

    @DeleteMapping("/delete-car")
    ResponseEntity<GetCarFullInfo> deleteCar(String car_uid){
        return carService.deleteCar(car_uid);
    }
}
