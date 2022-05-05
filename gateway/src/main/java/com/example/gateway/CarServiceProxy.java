package com.example.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "car", url = "https://lab2-egorychev-car.herokuapp.com/api/v1")
//@FeignClient(name = "car", url = "http://localhost:8001/api/v1")
public interface CarServiceProxy {
    @GetMapping("/cars/{carUid}")
    ResponseEntity<Car> getCar(@PathVariable("carUid") UUID carUid);

    @GetMapping("/cars")
    ResponseEntity<CarFullInfo> getCars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) Boolean showAll);

    @PatchMapping("/cars/{carUid}")
    ResponseEntity<HttpStatus> updateCar(@PathVariable("carUid") UUID carUid, @RequestParam Boolean availability);
}
