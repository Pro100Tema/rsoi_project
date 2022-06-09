package com.example.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "statistic", url = "http://localhost:8082/api/v1")
public interface StatisticClient {


    @RequestMapping(method = RequestMethod.POST, value = "/user-reg")
    ResponseEntity<Object> addUserRegistrationStat(@RequestHeader("uid") String uid);

    @RequestMapping(method = RequestMethod.GET, value = "/user-stat")
    ResponseEntity<List<UserStat>> getUserStat();
}
