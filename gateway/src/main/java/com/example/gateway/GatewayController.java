package com.example.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1")
public class GatewayController {
    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/cars")
    public ResponseEntity<PaginationResponse> getCars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) Boolean showAll) {
        return gatewayService.getCars(page, size, showAll);
    }

    @GetMapping("/rental")
    public ResponseEntity<List<RentalResponse>> getRentals(@RequestHeader("X-User-Name") String username) {
        return gatewayService.getRentals(username);
    }

    @GetMapping("/rental/{rentalUid}")
    public ResponseEntity<RentalResponse> getRental(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid) {
        return gatewayService.getRental(username, rentalUid);
    }

    @DeleteMapping("/rental/{rentalUid}")
    public ResponseEntity<HttpStatus> cancelRentalAndPaymentAndUnReserveCar(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid) {
        return gatewayService.cancelRentalAndPaymentAndUnReserveCar(username, rentalUid);
    }

    @PostMapping("/rental/{rentalUid}/finish")
    public ResponseEntity<HttpStatus> finishRentalAndUnReserveCar(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid) {
        return gatewayService.finishRentalAndUnReserveCar(username, rentalUid);
    }

    @PostMapping("/rental")
    public ResponseEntity<CreateRentalResponse> reserveCarAndCreateRentalAndPayment(@RequestHeader("X-User-Name") String username, @RequestBody CreateRentalRequest createRentalRequest) {
        return gatewayService.reserveCarAndCreateRentalAndPayment(username, createRentalRequest);
    }
}