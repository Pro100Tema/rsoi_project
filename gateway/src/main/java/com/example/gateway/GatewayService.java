package com.example.gateway;

import com.example.gateway.queue.QueueRequest;
import com.example.gateway.queue.QueueService;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class GatewayService {
    private final CarServiceProxy carServiceProxy;
    private final RentalServiceProxy rentalServiceProxy;
    private final PaymentServiceProxy paymentServiceProxy;
    private final QueueService queueService;

    @Autowired
    SessionClient sessionClient;

    @Autowired
    StatisticClient statisticClient;

    private static BlockingQueue<String> userStatQueue = new ArrayBlockingQueue<>(100);

    public GatewayService(CarServiceProxy carServiceProxy, RentalServiceProxy rentalServiceProxy, PaymentServiceProxy paymentServiceProxy, QueueService queueService) {
        this.carServiceProxy = carServiceProxy;
        this.rentalServiceProxy = rentalServiceProxy;
        this.paymentServiceProxy = paymentServiceProxy;
        this.queueService = queueService;
    }

    public ResponseEntity<Car> getCar(String jwt, UUID uid) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity(carServiceProxy.getCar(uid), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new Car(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new Car(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<GetCarFullInfo> getCarFullInfo(String jwt, UUID uid) {
        try {
//            ValidateToken validateToken = sessionClient.validate(jwt);
//            if (validateToken.getLogin() != null) {
            if (jwt != null && jwt.length() > 5) {
                return carServiceProxy.getCarFullInfo(uid);
                /*return new ResponseEntity<GetCarFullInfo>(
                        (MultiValueMap<String, String>) carServiceProxy.getCarFullInfo(uid),
                        HttpStatus.OK
                );*/
            } else {
                return new ResponseEntity<>(new GetCarFullInfo(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new GetCarFullInfo(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<CarFullInfo> getCars(Integer page, Integer size, Boolean showAll) {
        List<CarResponse> carResponses = new ArrayList<>();
        try {
            return carServiceProxy.getCars(page, size, showAll);
            /*ResponseEntity<CarFullInfo> answer = carServiceProxy.getCars(page, size, showAll);
            for (Car car : Objects.requireNonNull(answer.getBody()).getCars()) {
                carResponses.add(new CarResponse(car.getId(), car.getCar_uid(), car.getBrand(), car.getModel(), car.getRegistration_number(), car.getPower(), car.getPrice(), car.getType(), car.getAvailability()));
            }
            return new ResponseEntity<>(new PaginationResponse(page, size, answer.getBody().getTotalElements(), carResponses), answer.getStatusCode());*/
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

    public ResponseEntity<UserResponse> getUser(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity<>(sessionClient.getUser(validateToken.getLogin()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new UserResponse(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<UserStat>> getUserStat(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                ResponseEntity<List<UserStat>> response = statisticClient.getUserStat();
                if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody().size() > 0) {
                    List<UserStat> userStatDtoList = sessionClient.getUserStat(response.getBody());
                    return new ResponseEntity<>(userStatDtoList, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ValidateToken validate(String jwt) {
        try {
            return sessionClient.validate(jwt);
        } catch (Exception e) {
            return new ValidateToken();
        }
    }

    public ResponseEntity<GetCarFullInfo> createCar(String jwt, GetCarFullInfo car) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return carServiceProxy.createCar(car);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<RentalInfo>> getRentals(String jwt, String username) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return rentalServiceProxy.getRentals(username);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
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
            rentalServiceProxy.createRental(new RentalInfo());
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

    public ResponseEntity<PaymentCarInfo> paymentForCar(String jwt, PaymentInfo paymentInfo) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                UserResponse userResponse = sessionClient.getUser(validateToken.getLogin());
                return paymentServiceProxy.paymentForCar(userResponse.getUser_uid().toString(), paymentInfo);
                //return new ResponseEntity<>(new ticketResponse, HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<GetCarFullInfo> changeAvailability(String jwt, GetCarFullInfo carResponse) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return carServiceProxy.changeAvailability(carResponse);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<RentalInfo> createRental(String jwt, RentalInfo rentalInfo) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return rentalServiceProxy.createRental(rentalInfo);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<GetCarFullInfo> deleteCar(String jwt, UUID car_uid) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return carServiceProxy.deleteCar(car_uid.toString());
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> addUserRegistrationStat(String jwt) {
        ResponseEntity<UserResponse> user = this.getUser(jwt);
        if (user.getStatusCode().equals(HttpStatus.OK)) {
            try {
                return statisticClient.addUserRegistrationStat(user.getBody().getUser_uid().toString());
            } catch (Exception e) {
                userStatQueue.add(user.getBody().getUser_uid().toString());
                UserStatConsumer userStatConsumer = new UserStatConsumer(statisticClient);
                userStatConsumer.start();
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Component
    public static class UserStatConsumer extends Thread {

        private final StatisticClient statisticClient;

        @Autowired
        UserStatConsumer(StatisticClient statisticClient) {
            this.statisticClient = statisticClient;
        }

        @Override
        public void run() {
            try {
                while (!GatewayService.userStatQueue.isEmpty()) {
                    Thread.sleep(5000);
                    String request = userStatQueue.take();
                    try {
                        statisticClient.addUserRegistrationStat(request);
                    } catch (Exception e) {
                        userStatQueue.add(request);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}