package com.example.front.service;

import com.example.front.dto.*;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import org.atmosphere.config.service.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VaadinService {

    @Autowired
    private SessionClient sessionClient;
    @Autowired
    private GatewayClient gatewayClient;

    public ResponseEntity<AuthResponce> authenticate(String username, String password) {
        try {
            ResponseEntity<AuthResponce> response = sessionClient.authorization(username, password);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                AuthResponce authResponse = response.getBody();
                Cookie myCookie = new Cookie("jwt", authResponse.getJwt());
                myCookie.setMaxAge(10 * 60);
                myCookie.setPath("/");
                VaadinResponse.getCurrent().addCookie(myCookie);
                return response;
            } else {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<AuthResponce> registration(String login, String name, String surname, String password) {
        try {
            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setLogin(login);
            registrationRequest.setName(name);
            registrationRequest.setSurname(surname);
            registrationRequest.setPassword(password);
            ResponseEntity<AuthResponce> response = sessionClient.registration(registrationRequest);
            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                AuthResponce authResponse = response.getBody();
                Cookie myCookie = new Cookie("jwt", authResponse.getJwt());
                myCookie.setMaxAge(10 * 60);
                myCookie.setPath("/");
                VaadinResponse.getCurrent().addCookie(myCookie);
                try {
                    ResponseEntity<Object> responseEntity = gatewayClient.addUserRegistrationStat("Bearer " + myCookie.getValue());
                    return response;
                } catch (Exception e) {
                    return response;
                }
            }
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity userCheck(String username) {
        return sessionClient.userCheck(username);
    }

    public ValidateToken validate(String jwt) {
        try {
            ValidateToken validateToken = gatewayClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return validateToken;
            }
            return new ValidateToken();
        } catch (Exception e) {
            return new ValidateToken();
        }
    }

    public String getJWT() {
        StringBuilder jwt = new StringBuilder("Bearer ");
        Cookie[] cookies = VaadinRequest.getCurrent().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                jwt.append(cookie.getValue());
            }
        }
        return jwt.toString();
    }

    public ResponseEntity<GetCarFullInfo> getCar(UUID carUid) {
        try {
            //System.out.println(carUid);
            System.out.println(getJWT());
            return gatewayClient.getCarFullInfo(getJWT(), carUid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<CarFullInfo> getCars(int page, int size, boolean showAll) {
        try {
            return gatewayClient.getCars(page, size, showAll);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<GetCarFullInfo> createCar(GetCarFullInfo car) {
        try {
            return gatewayClient.createCar(getJWT(), car);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<UserResponse> getUser() {
        try {
            return gatewayClient.getUser(getJWT());
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<UserStat>> getUserStat() {
        try {
            return gatewayClient.getUserStat(getJWT());
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<HttpStatus> updateCar(UUID carUid, Boolean availability) {
        try {
            return gatewayClient.updateCar(getJWT(), carUid, availability);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Payment> getPayment(UUID paymentUid) {
        try {
            return gatewayClient.getPayment(getJWT(), paymentUid);
        } catch (Exception e) {
            return new ResponseEntity<>(new Payment(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<HttpStatus> cancelPayment(UUID paymentUid) {
        try {
            return gatewayClient.cancelPayment(getJWT(), paymentUid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<HttpStatus> createPayment(Payment payment) {
        try {
            return gatewayClient.createPayment(getJWT(), payment);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<RentalInfo>> getRentals(String username) {
        try {
            return gatewayClient.getRentals(getJWT(), username);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Rental> getRental(String username, UUID rentalUid) {
        try {
            return gatewayClient.getRental(getJWT(), username, rentalUid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<CarAndPaymentInfo> cancelRental(String username, UUID rentalUid) {
        try {
            return gatewayClient.cancelRental(getJWT(), username, rentalUid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<UUID> finishRental(String username, UUID rentalUid) {
        try {
            return gatewayClient.finishRental(getJWT(), username, rentalUid);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<RentalInfo> createRental(RentalInfo rentalInfo) {
        try {
            return gatewayClient.createRental(getJWT(), rentalInfo);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<PaymentInfo> paymentForCar(PaymentInfo paymentInfo) {
        try {
            return gatewayClient.paymentForCar(getJWT(), paymentInfo);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<GetCarFullInfo> changeAvailability(GetCarFullInfo carResponse) {
        try{
            return gatewayClient.changeAvailability(getJWT(), carResponse);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<GetCarFullInfo> deleteCar(String car_uid) {
        try{
            return gatewayClient.deleteCar(getJWT(), car_uid);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
