package com.example.gateway;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient(name = "session", url = "http://localhost:8084/api/v1")
public interface SessionClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user", consumes = APPLICATION_FORM_URLENCODED_VALUE)
    UserResponse getUser(@RequestParam("login") String login);

    @RequestMapping(method = RequestMethod.GET, value = "/validate")
    ValidateToken validate(@RequestHeader("jwt") String jwt);

    @RequestMapping(method = RequestMethod.PUT, value = "/user-stat")
    List<UserStat> getUserStat(@RequestBody List<UserStat> userStatDtoList);

}
