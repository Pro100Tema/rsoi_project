package com.example.payment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public ResponseEntity<Payment> getPayment(UUID paymentUid) {
        try {
            Payment payment = paymentRepository.findByPayment_uid(paymentUid);
            return new ResponseEntity<>(payment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> cancelPayment(UUID paymentUid) {
        try {
            Payment payment = paymentRepository.findByPayment_uid(paymentUid);
            payment.setStatus(PaymentStatus.CANCELED);
            paymentRepository.save(payment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> createPayment(Payment payment) {
        try {
            //System.out.println("create payment");
            paymentRepository.save(new Payment(payment.getPayment_uid(), payment.getUser_uid(), payment.getStatus(), payment.getPrice()));
            System.out.println(paymentRepository);
            System.out.println(payment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<PaymentInfo> paymentForCar(String user_uid, PaymentInfo paymentInfo) {
        try {
            Payment payment = new Payment();
            payment.setPrice(paymentInfo.getPrice());
            UUID payment_uuid = UUID.randomUUID();
            payment.setPayment_uid(payment_uuid);
            payment.setUser_uid(UUID.fromString(user_uid));
            payment.setStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);
            paymentInfo.setPayment_uid(payment_uuid.toString());
            return new ResponseEntity<>(paymentInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new PaymentInfo(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}