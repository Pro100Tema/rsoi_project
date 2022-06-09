package com.example.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@FeignClient(name = "rental", url = "https://lab2-egorychev-rental.herokuapp.com/api/v1")
@FeignClient(name = "rental", url = "http://localhost:8002/api/v1")
public interface RentalServiceProxy {

    @GetMapping("/rental")
    ResponseEntity<List<RentalInfo>> getRentals(String username);

    @GetMapping("/rental/{rentalUid}")
    ResponseEntity<Rental> getRental(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rental/{rentalUid}")
    ResponseEntity<CarAndPaymentInfo> cancelRental(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid);

    @DeleteMapping("/rental/{rentalUid}/finish")
    ResponseEntity<UUID> finishRental(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid);

    @PostMapping("/create-rental")
    ResponseEntity<RentalInfo> createRental(@RequestBody RentalInfo rentalInfo);
}
