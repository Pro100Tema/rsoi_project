package com.example.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("https://localhost:8084/api/v1")
@RequestMapping("api/v1")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/authenticate")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthResponse> getAuth(@RequestParam("username") String username, @RequestParam("password") String password) {
        return sessionService.getAuth(username, password);
    }

    @PostMapping("/registration")
    @CrossOrigin(origins = "*")
    public ResponseEntity<AuthResponse> registration(@RequestBody RegistrationRequest registrationRequest) {
        return sessionService.registration(registrationRequest);
    }

    @GetMapping("/user-check")
    @CrossOrigin(origins = "*")
    public ResponseEntity userCheck(@RequestParam("username") String username) {
        return sessionService.userCheck(username);
    }

    @GetMapping("/validate")
    @CrossOrigin(origins = "*")
    public ValidateToken validate(@RequestHeader("jwt") String jwt) {
        return sessionService.validate(jwt);
    }

    @GetMapping("/user")
    @CrossOrigin(origins = "*")
    public UserResponse getUser(@RequestParam("login") String login) {
        UserResponse userResponse = sessionService.getUser(login);
        if (userResponse != null) {
            return userResponse;
        }
        return new UserResponse();
    }

    @PutMapping("/user-stat")
    @CrossOrigin(origins = "*")
    public List<UserStat> getUserStat(@RequestBody List<UserStat> userStatDtoList) {
        return sessionService.getUserStat(userStatDtoList);
    }
}
