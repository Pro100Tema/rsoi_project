package com.example.rental;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/rental")
    public ResponseEntity<List<Rental>> getRentals(@RequestHeader("X-User-Name") String username) {
        return rentalService.getRentals(username);
    }

    @GetMapping("/rental/{rentalUid}")
    public ResponseEntity<Rental> getRental(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.getRental(username, rentalUid);
    }

    @DeleteMapping("/rental/{rentalUid}")
    public ResponseEntity<CarAndPaymentInfo> cancelRental(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.cancelRental(username, rentalUid);
    }

    @DeleteMapping("/rental/{rentalUid}/finish")
    public ResponseEntity<UUID> finishRental(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid) {
        return rentalService.finishRental(username, rentalUid);
    }

    @PostMapping("/rental")
    public ResponseEntity<HttpStatus> createRental(@RequestBody Rental rental) {
        return rentalService.createRental(rental);
    }
}
