package com.example.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("SessionService")
public class SessionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtToken jwtToken;

    public ResponseEntity<AuthResponse> getAuth(String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            UserResponse userResponse = new UserResponse();
            userResponse.setLogin(user.getLogin());
            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jwtToken.generateToken(userResponse));
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(new AuthResponse(), HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<AuthResponse> registration(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setLogin(registrationRequest.getLogin());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        user.setPassword(hashedPassword);
        user.setName(registrationRequest.getName());
        user.setSurname(registrationRequest.getSurname());
        user.setRole(Role.USER);
        user.setUser_uid(UUID.randomUUID());
        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setLogin(registrationRequest.getLogin());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtToken.generateToken(userResponse));
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    public ResponseEntity userCheck(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ValidateToken validate(String jwt) {
        ValidateToken validateToken = new ValidateToken();
        try {
            jwt = jwt.substring(7);
            String login = jwtToken.getUsernameFromToken(jwt);
            if (jwtToken.validateToken(jwt, login)) {
                validateToken.setLogin(login);
                return validateToken;
            }
            return new ValidateToken();
        } catch (Exception e) {
            return new ValidateToken();
        }
    }

    public UserResponse getUser(String login) {
        User user = userRepository.findByUsername(login);
        if (user != null) {
            UserResponse userResponse = new UserResponse();
            userResponse.setLogin(user.getLogin());
            userResponse.setName(user.getName());
            userResponse.setSurname(user.getSurname());
            userResponse.setRole(user.getRole().toString());
            userResponse.setUser_uid(user.getUser_uid());
            userResponse.setId(user.getId());
            return userResponse;
        }
        else {
            return null;
        }
    }

    public List<UserStat> getUserStat(List<UserStat> userStatDtoList) {
        List<UserStat> us = new ArrayList<>();
        for (UserStat userStatDto : userStatDtoList) {
            User user = userRepository.findByUuid(UUID.fromString(userStatDto.getUser_uid()));
            userStatDto.setName(user.getName() + " " + user.getSurname());
            userStatDto.setUsername(user.getLogin());
            us.add(userStatDto);
        }
        return us;
    }
}
