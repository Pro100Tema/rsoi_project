package com.example.rental;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public ResponseEntity<List<Rental>> getRentals(String username) {
        try {
            List<Rental> rental = rentalRepository.findByUsername(username);
            return new ResponseEntity(rental, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Rental> getRental(String username, UUID rentalUid) {
        try {
            Rental rental = rentalRepository.findByUsernameAndRental_uid(username, rentalUid);
            return new ResponseEntity<>(rental, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<CarAndPaymentInfo> cancelRental(String username, UUID rentalUid) {
        try {
            Rental rental = rentalRepository.findByUsernameAndRental_uid(username, rentalUid);
            rental.setStatus(RentalStatus.CANCELED);
            rentalRepository.save(rental);
            return new ResponseEntity<>(new CarAndPaymentInfo(rental.getCar_uid(), rental.getPayment_uid()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<UUID> finishRental(String username, UUID rentalUid) {
        try {
            Rental rental = rentalRepository.findByUsernameAndRental_uid(username, rentalUid);
            rental.setStatus(RentalStatus.FINISHED);
            rentalRepository.save(rental);
            return new ResponseEntity<>(rental.getCar_uid(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> createRental(Rental rental) {
        try {
            rentalRepository.save(new Rental(rental.getRental_uid(), rental.getUsername(), rental.getPayment_uid(),
                    rental.getCar_uid(), rental.getDate_from(), rental.getDate_to(), rental.getStatus()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
