package com.example.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//@FeignClient(name = "payment", url = "https://lab2-egorychev-payment.herokuapp.com/api/v1")
@FeignClient(name = "payment", url = "http://localhost:8003/api/v1")
public interface PaymentServiceProxy {

    @GetMapping("/payments/{paymentUid}")
    ResponseEntity<Payment> getPayment(@PathVariable("paymentUid") UUID paymentUid);

    @DeleteMapping("/payments/{paymentUid}")
    ResponseEntity<HttpStatus> cancelPayment(@PathVariable("paymentUid") UUID paymentUid);

    @PostMapping("/payments")
    ResponseEntity<HttpStatus> createPayment(@RequestBody Payment payment);

    @PostMapping("/pay")
    public ResponseEntity<PaymentCarInfo> paymentForCar(@RequestHeader("user_uid") String user_uid, @RequestBody PaymentInfo paymentInfo);
}
