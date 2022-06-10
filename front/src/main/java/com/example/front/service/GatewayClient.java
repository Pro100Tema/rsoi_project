package com.example.front.service;


import com.example.front.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "gateway", url = "http://localhost:8080/api/v1")
public interface GatewayClient {

   // @GetMapping("/car/{carUid}")
   // public ResponseEntity<Car> getCar(@RequestHeader("jwt") String jwt, @PathVariable("carUid") UUID carUid);

    @GetMapping("/car-info/{carUid}")
    public ResponseEntity<GetCarFullInfo> getCarFullInfo(@RequestHeader("jwt") String jwt, @PathVariable("carUid") UUID carUid);

    @GetMapping("/cars")
    public ResponseEntity<CarFullInfo> getCars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) Boolean showAll);

    @PatchMapping("/cars/{carUid}")
    public ResponseEntity<HttpStatus> updateCar(@RequestHeader("jwt") String jwt, @PathVariable("carUid") UUID carUid, @RequestParam Boolean availability);

    @GetMapping("/payments/{paymentUid}")
    public ResponseEntity<Payment> getPayment(@RequestHeader("jwt") String jwt,@PathVariable("paymentUid") UUID paymentUid);

    @DeleteMapping("/payments/{paymentUid}")
    public ResponseEntity<HttpStatus> cancelPayment(@RequestHeader("jwt") String jwt,@PathVariable("paymentUid") UUID paymentUid);

    @PostMapping("/payments")
    public ResponseEntity<HttpStatus> createPayment(@RequestHeader("jwt") String jwt,@RequestBody Payment payment);

    @GetMapping("/rental")
    public ResponseEntity<List<RentalInfo>> getRentals(@RequestHeader("jwt") String jwt,@RequestHeader("X-User-Name") String username);

    @GetMapping("/rental/{rentalUid}")
    public ResponseEntity<Rental> getRental(@RequestHeader("jwt") String jwt,@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rental/{rentalUid}")
    public ResponseEntity<CarAndPaymentInfo> cancelRental(@RequestHeader("jwt") String jwt,@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rental/{rentalUid}/finish")
    public ResponseEntity<UUID> finishRental(@RequestHeader("jwt") String jwt,@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid);

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    public ValidateToken validate(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public ResponseEntity<UserResponse> getUser(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.GET, value = "/user-stat")
    public ResponseEntity<List<UserStat>> getUserStat(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.POST, value = "/new-car")
    ResponseEntity<GetCarFullInfo> createCar(@RequestHeader("jwt") String jwt, @RequestBody GetCarFullInfo car);

    @RequestMapping(method = RequestMethod.POST, value = "/availability")
    ResponseEntity<GetCarFullInfo> changeAvailability(@RequestHeader("jwt") String jwt, @RequestBody GetCarFullInfo carResponse);

    @RequestMapping(method = RequestMethod.POST, value = "/pay")
    ResponseEntity<PaymentInfo> paymentForCar(@RequestHeader("jwt") String jwt, @RequestBody PaymentInfo paymentInfo);

    @PostMapping("/create-rental")
    public ResponseEntity<RentalInfo> createRental(@RequestHeader("jwt") String jwt,@RequestBody RentalInfo rentalInfo);

    @DeleteMapping("/delete-car")
    ResponseEntity<GetCarFullInfo> deleteCar(String jwt, @PathVariable("carUid") String car_uid);

    @RequestMapping(method = RequestMethod.POST, value = "/user-reg")
    ResponseEntity<Object> addUserRegistrationStat(@RequestHeader("jwt") String uid);
}
