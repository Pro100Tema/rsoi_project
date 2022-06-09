package com.example.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@FeignClient(name = "car", url = "https://lab2-egorychev-car.herokuapp.com/api/v1")
@FeignClient(name = "car", url = "http://localhost:8001/api/v1")
public interface CarServiceProxy {
    @GetMapping("/car/{carUid}")
    ResponseEntity<Car> getCar(@PathVariable("carUid") UUID carUid);

    @GetMapping("/car-info/{carUid}")
    ResponseEntity<GetCarFullInfo> getCarFullInfo(@PathVariable("carUid") UUID carUid);

    @GetMapping("/cars")
    ResponseEntity<CarFullInfo> getCars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) Boolean showAll);

    @PatchMapping("/cars/{carUid}")
    ResponseEntity<HttpStatus> updateCar(@PathVariable("carUid") UUID carUid, @RequestParam Boolean availability);

    @RequestMapping(value = "/new-car")
    ResponseEntity<GetCarFullInfo> createCar(@RequestBody GetCarFullInfo car);

    @RequestMapping(method = RequestMethod.POST, value = "/availability")
    ResponseEntity<GetCarFullInfo> changeAvailability(@RequestBody GetCarFullInfo carResponse);

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete-car")
    ResponseEntity<GetCarFullInfo> deleteCar(String car_uid);
}
