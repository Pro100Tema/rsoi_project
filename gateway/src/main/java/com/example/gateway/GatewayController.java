package com.example.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("https://localhost:8080/api/v1")
@RequestMapping("api/v1")
public class GatewayController {
    private final GatewayService gatewayService;

    public GatewayController(GatewayService gatewayService) {
        this.gatewayService = gatewayService;
    }

    @GetMapping("/car-info/{carUid}")
    public ResponseEntity<GetCarFullInfo> getCarFullInfo(@RequestHeader("jwt") String jwt, @PathVariable("carUid") UUID uid) {
        //return gatewayService.getCar(jwt, uid);
        //GetCarFullInfo getcar = new GetCarFullInfo();
        //getcar.setBrand("пизда");
        //System.out.println("lol");
        return gatewayService.getCarFullInfo(jwt, uid);
        //return new ResponseEntity<>(getcar, HttpStatus.OK);
    }

    @GetMapping("/cars")
    public ResponseEntity<CarFullInfo> getCars(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) Boolean showAll) {
        return gatewayService.getCars(page, size, showAll);
    }

    @GetMapping("/rental")
    public ResponseEntity<List<RentalInfo>> getRentals(@RequestHeader("jwt") String jwt, String username) {
        return gatewayService.getRentals(jwt, username);
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

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getUser(@RequestHeader("jwt") String jwt) {
        return gatewayService.getUser(jwt);
    }

    @GetMapping("/validate")
    @CrossOrigin(origins = "*")
    public ValidateToken validate(@RequestHeader("jwt") String jwt) {
        return gatewayService.validate(jwt);
    }

    @GetMapping("/user-stat")
    public ResponseEntity<List<UserStat>> getUserStat(@RequestHeader("jwt") String jwt) {
        return gatewayService.getUserStat(jwt);
    }

    @PostMapping("/pay")
    public ResponseEntity<PaymentCarInfo> paymentForCar(@RequestHeader("jwt") String jwt, @RequestBody PaymentInfo paymentInfo) {
        return gatewayService.paymentForCar(jwt, paymentInfo);
    }

    @PostMapping("/availability")
    public ResponseEntity<GetCarFullInfo> changeAvailability(@RequestHeader("jwt") String jwt, @RequestBody GetCarFullInfo carResponse) {
        return gatewayService.changeAvailability(jwt, carResponse);
    }

    @PostMapping("/create-rental")
    public ResponseEntity<RentalInfo> createRental(@RequestHeader("jwt") String jwt,@RequestBody RentalInfo rentalInfo){
        return gatewayService.createRental(jwt, rentalInfo);
    }

    @PostMapping("/new-car")
    ResponseEntity<GetCarFullInfo> createCar(@RequestHeader("jwt") String jwt, @RequestBody GetCarFullInfo car){
        return gatewayService.createCar(jwt,car);
    }

    @DeleteMapping("/delete-car")
    ResponseEntity<GetCarFullInfo> deleteCar(String jwt, @PathVariable("carUid") UUID car_uid){
        return gatewayService.deleteCar(jwt, car_uid);
    }

    @PostMapping("/user-reg")
    public ResponseEntity<Object> addUserRegistrationStat(@RequestHeader("jwt") String jwt) {
        return gatewayService.addUserRegistrationStat(jwt);
    }

}