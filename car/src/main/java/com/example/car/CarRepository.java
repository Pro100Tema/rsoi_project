package com.example.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("select c from Car c where c.car_uid = ?1")
    Car findByCar_uid(UUID car_uid);
}
