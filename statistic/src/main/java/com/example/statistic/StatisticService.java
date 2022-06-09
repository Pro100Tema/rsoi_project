package com.example.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service("StatisticService")
public class StatisticService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    public ResponseEntity<Object> addUserRegistrationStat(String uid) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setUser_uid(UUID.fromString(uid));
        userRegistration.setDateOfRegistration(LocalDateTime.now());
        userRegistrationRepository.save(userRegistration);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<UserStat>> getUserStat() {
        List<UserRegistration> userRegistrationsList = userRegistrationRepository.findAll();
        List<UserStat> userStatDtoList = new ArrayList<>();
        for (UserRegistration userRegistration : userRegistrationsList) {
            UserStat userStatDto = new UserStat();
            userStatDto.setUser_uid(userRegistration.getUser_uid().toString());
            userStatDto.setRegisterDate(userRegistration.getDateOfRegistration());
            String date = DateTimeFormatter.ofPattern("d MMMM yyyy")
                    .withLocale(new Locale("ru"))
                    .format((userRegistration.getDateOfRegistration()));
            userStatDto.setStringRegisterDate(date);
            userStatDtoList.add(userStatDto);
        }
        return new ResponseEntity<>(userStatDtoList, HttpStatus.OK);
    }
}
