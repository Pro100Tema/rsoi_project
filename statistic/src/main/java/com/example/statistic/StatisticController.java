package com.example.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("https://localhost:8082/api/v1")
@RequestMapping("api/v1")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @PostMapping("/user-reg")
    public ResponseEntity<Object> addUserRegistrationStat(@RequestHeader("uid") String uid) {
        return statisticService.addUserRegistrationStat(uid);
    }

    @GetMapping("/user-stat")
    public ResponseEntity<List<UserStat>> getUserStat() {
        return statisticService.getUserStat();
    }
}
