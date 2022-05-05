package com.example.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/{carUid}")
    public ResponseEntity<Car> getCar(@PathVariable("carUid") UUID carUid) {
        return carService.getCar(carUid);
    }

    @GetMapping("/cars")
    public ResponseEntity<CarFullInfo> getCars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) Boolean showAll) {
        return carService.getCars(page, size, showAll);
    }

    @PatchMapping("/cars/{carUid}")
    public ResponseEntity<HttpStatus> updateCar(@PathVariable("carUid") UUID carUid, @RequestParam Boolean availability) {
        return carService.updateCar(carUid, availability);
    }
}
