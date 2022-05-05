package com.example.gateway;

import com.example.lab2.gateway.queue.QueueRequest;
import com.example.lab2.gateway.queue.QueueService;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class GatewayService {
    private final CarServiceProxy carServiceProxy;
    private final RentalServiceProxy rentalServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;
    private final QueueService queueService;

    public GatewayService(CarServiceProxy carServiceProxy, RentalServiceProxy rentalServiceProxy, PaymentServiceProxy paymentServiceProxy, QueueService queueService) {
        this.carServiceProxy = carServiceProxy;
        this.rentalServiceProxy = rentalServiceProxy;
        this.paymentServiceProxy = paymentServiceProxy;
        this.queueService = queueService;
    }

    public ResponseEntity<PaginationResponse> getCars(Integer page, Integer size, Boolean showAll) {
        List<CarResponse> carResponses = new ArrayList<>();
        try {
            ResponseEntity<CarFullInfo> answer = carServiceProxy.getCars(page, size, showAll);
            for (Car car : Objects.requireNonNull(answer.getBody()).getCars()) {
                carResponses.add(new CarResponse(car.getCar_uid(), car.getBrand(), car.getModel(), car.getRegistration_number(), car.getPower(), car.getPrice(), car.getType(), car.getAvailability()));
            }
            return new ResponseEntity<>(new PaginationResponse(page, size, answer.getBody().getTotalElements(), carResponses), answer.getStatusCode());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<RentalResponse> checkRentalResponse(Rental rental, List<RentalResponse> rentalResponses, Boolean car_flag, Boolean payment_flag, Car car, Payment payment){
        if (payment_flag && car_flag) {
            rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                    rental.getStatus(), null, null));
        } else if (payment_flag) {
            rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                    rental.getStatus(), new CarInfo(rental.getCar_uid(), car.getBrand(), car.getModel(), car.getRegistration_number()), null));
        } else if (car_flag) {
            rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                    rental.getStatus(), null, new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())));
        } else {
            rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                    rental.getStatus(), new CarInfo(rental.getCar_uid(), car.getBrand(), car.getModel(), car.getRegistration_number()),
                    new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())));
        }
        return rentalResponses;
    }


    public ResponseEntity<List<RentalResponse>> getRentals(String username) {
        //System.out.println("gateway get rentals");
        List<RentalResponse> rentalResponses = new ArrayList<>();
        ResponseEntity<List<Rental>> rentals;
        try {
            rentals = rentalServiceProxy.getRentals(username);
        } catch (FeignException e) {
            //return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
            throw new FeignMessageException("Rental Service unavailable");
        }

        for (Rental rental : rentals.getBody()) {
            Car car;
            Payment payment;
                //Boolean car_flag = false;
                //Boolean payment_flag = false;
                //System.out.println(rental);
            try {
                car = carServiceProxy.getCar(rental.getCar_uid()).getBody();
                    //System.out.println("+++");
            } catch (FeignException e) {
                car = null;
                    //System.out.println("car null");
                    //car_flag = true;
            }

            try {
                payment = paymentServiceProxy.getPayment(rental.getPayment_uid()).getBody();
                    //System.out.println("++++");
            } catch (FeignException e) {
                payment = null;
                    //System.out.println("pay null");
                    //payment_flag = true;
            }
                //checkRentalResponse(rental, rentalResponses, car_flag, payment_flag, car, payment);
            if (payment == null && car == null) {
                    //System.out.println("car i pay null");
                rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(), rental.getStatus(), null, null));
            } else if (payment == null) {
                    //System.out.println("pay null");
                rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                        rental.getStatus(), new CarInfo(rental.getCar_uid(), car.getBrand(), car.getModel(), car.getRegistration_number()), null));
            } else if (car == null) {
                    //System.out.println("car null");
                rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                        rental.getStatus(), null, new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())));
            } else {
                    //System.out.println("elsse");
                rentalResponses.add(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(),
                        rental.getStatus(), new CarInfo(rental.getCar_uid(), car.getBrand(), car.getModel(), car.getRegistration_number()),
                        new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())));
            }
        }
        return new ResponseEntity<>(rentalResponses, rentals.getStatusCode());
    }


    public ResponseEntity<RentalResponse> getRental(String username, UUID rentalUid) {
        ResponseEntity<Rental> rentalResponseEntity;
        Rental rental;
        Car car;
        Payment payment;
        Boolean car_flag = false;
        Boolean payment_flag = false;
        try {
            rentalResponseEntity = rentalServiceProxy.getRental(username, rentalUid);
            rental = rentalResponseEntity.getBody();
        } catch (FeignException e) {
            //return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            throw new FeignMessageException("Rental Service unavailable");
        }

        try {
            car = carServiceProxy.getCar(rental.getCar_uid()).getBody();
        } catch (FeignException e) {
            car = null;
            car_flag = true;
        }

        try {
            payment = paymentServiceProxy.getPayment(rental.getPayment_uid()).getBody();
        } catch (FeignException e) {
            payment = null;
            payment_flag = true;
        }

        if (payment_flag && car_flag) {
            return new ResponseEntity<>(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(), rental.getStatus(), null, null),
                    rentalResponseEntity.getStatusCode());
        } else if (payment_flag) {
            return new ResponseEntity<>(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(), rental.getStatus(), new CarInfo(rental.getCar_uid(), car.getBrand(), car.getModel(), car.getRegistration_number()), null),
                    rentalResponseEntity.getStatusCode());
        } else if (car_flag) {
            return new ResponseEntity<>(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(), rental.getStatus(), null, new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())),
                    rentalResponseEntity.getStatusCode());
        } else {
            return new ResponseEntity<>(new RentalResponse(rental.getRental_uid(), rental.getDate_from_string(), rental.getDate_to_string(), rental.getStatus(), new CarInfo(rental.getCar_uid(), car.getBrand(), car.getModel(), car.getRegistration_number()),
                    new PaymentInfo(rental.getPayment_uid(), payment.getStatus(), payment.getPrice())),
                    rentalResponseEntity.getStatusCode());
        }
    }

    @DeleteMapping("/rental/{rentalUid}")
    public ResponseEntity<HttpStatus> cancelRentalAndPaymentAndUnReserveCar(@RequestHeader("X-User-Name") String username, @PathVariable("rentalUid") UUID rentalUid) {
        ResponseEntity<CarAndPaymentInfo> responseEntity;
        try {
            responseEntity = rentalServiceProxy.cancelRental(username, rentalUid);
        } catch (FeignException e) {
            QueueRequest newReqForQueue = new QueueRequest();
            newReqForQueue.setCancelUserRental(username, rentalUid);
            queueService.putQueueRequest(newReqForQueue);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
            try {
                carServiceProxy.updateCar(responseEntity.getBody().getCarUid(), true);
            } catch (FeignException e) {
                QueueRequest newReqForQueue = new QueueRequest();
                newReqForQueue.setUpdateCarReserve(responseEntity.getBody().getCarUid(), true);
                queueService.putQueueRequest(newReqForQueue);
            }

            try {
                paymentServiceProxy.cancelPayment(responseEntity.getBody().getPaymentUid());
            } catch (FeignException e) {
                QueueRequest newReqForQueue = new QueueRequest();
                newReqForQueue.setCancelPayment(responseEntity.getBody().getPaymentUid());

                queueService.putQueueRequest(newReqForQueue);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<HttpStatus> finishRentalAndUnReserveCar(String username, UUID rentalUid) {
        ResponseEntity<UUID> responseEntity;
        try {
            responseEntity = rentalServiceProxy.finishRental(username, rentalUid);
        } catch (FeignException e) {
            QueueRequest newReqForQueue = new QueueRequest();
            newReqForQueue.setFinishUserRental(username, rentalUid);
            queueService.putQueueRequest(newReqForQueue);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        if (responseEntity.getStatusCode() != HttpStatus.NOT_FOUND) {
            try {
                carServiceProxy.updateCar(responseEntity.getBody(), true);
            } catch (FeignException e) {
                QueueRequest newReqForQueue = new QueueRequest();
                newReqForQueue.setUpdateCarReserve(responseEntity.getBody(), true);
                queueService.putQueueRequest(newReqForQueue);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<CreateRentalResponse> reserveCarAndCreateRentalAndPayment(String username, CreateRentalRequest createRentalRequest) {
        ResponseEntity<Car> carResponse;
        UUID paymentUid = UUID.randomUUID();
        UUID rentalUid = UUID.randomUUID();
        int price = 0;

        try {
            //System.out.println("first try");
            carResponse = carServiceProxy.getCar(createRentalRequest.getCarUid());
            //System.out.println(carResponse);
            Car car = carResponse.getBody();
            //System.out.println(car);
            price = ((int) TimeUnit.DAYS.convert(createRentalRequest.getDateTo().getTime() - createRentalRequest.getDateFrom().getTime(), TimeUnit.MILLISECONDS)) * car.getPrice();
            //System.out.println(price);
        } catch (FeignException e) {
            //System.out.println("+");
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }

        try {
            //System.out.println("secoond try");
            carServiceProxy.updateCar(createRentalRequest.getCarUid(), false);
            //System.out.println(carServiceProxy);
        } catch (FeignException e) {
            //System.out.println("++");
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }

        try {
            //System.out.println("third try");
            rentalServiceProxy.createRental(new Rental(rentalUid, username, paymentUid, createRentalRequest.getCarUid(),
                    createRentalRequest.getDateFrom(), createRentalRequest.getDateTo(), RentalStatus.IN_PROGRESS));
            //System.out.println(rentalServiceProxy);
        } catch (FeignException e) {
            //System.out.println("+++");
            carServiceProxy.updateCar(createRentalRequest.getCarUid(), true);
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }

        try {
            //System.out.println("4 try");
            //System.out.println(paymentUid);
            //System.out.println(PaymentStatus.PAID);
            //System.out.println(price);
            //System.out.println(new Payment(paymentUid, PaymentStatus.PAID, price));
            paymentServiceProxy.createPayment(new Payment(paymentUid, PaymentStatus.PAID, price));
            //System.out.println(paymentServiceProxy.createPayment(new Payment(paymentUid, PaymentStatus.PAID, price)));
        } catch (FeignException e) {
            //System.out.println("++++");
            rentalServiceProxy.cancelRental(username, rentalUid);
            //System.out.println(rentalServiceProxy);
            carServiceProxy.updateCar(createRentalRequest.getCarUid(), true);
            //System.out.println(carServiceProxy);
            //return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
            throw new FeignMessageException("Payment Service unavailable");
        }

        CreateRentalResponse createRentalResponse = new CreateRentalResponse(rentalUid, createRentalRequest.getCarUid(), createRentalRequest.getDateFromString(), createRentalRequest.getDateToString(),
                new PaymentInfo(paymentUid, PaymentStatus.PAID, price), RentalStatus.IN_PROGRESS);
        //System.out.println(createRentalResponse);
        return new ResponseEntity<>(createRentalResponse, HttpStatus.OK);
    }
}