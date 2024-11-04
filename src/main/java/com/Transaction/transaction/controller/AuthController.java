package com.Transaction.transaction.controller;

import com.Transaction.transaction.exception.PasswordIncorrectException;
import com.Transaction.transaction.model.ChangePasswordRequest;
import com.Transaction.transaction.model.JwtRequest;
import com.Transaction.transaction.model.JwtResponse;
import com.Transaction.transaction.model.User;
import com.Transaction.transaction.payloads.UserDto;
import com.Transaction.transaction.security.JwtService;
import com.Transaction.transaction.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest user) {
        JwtResponse jwtResponse;

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        if (encoder.matches(user.getPassword(), userDetails.getPassword())) {
            String token = this.jwtService.generateToken(userDetails);
            jwtResponse = JwtResponse.builder()
                    .token(token)
                    .email(userDetails.getUsername())
                    .build();

            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        } else {
            throw new PasswordIncorrectException("Invalid Password !");
        }
    }


    @PostMapping("/create_user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserDto userDto = this.userService.createUser(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }

    @PostMapping("/change-password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordRequest passwordRequest) {
        this.userService.changePassword(passwordRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/sent-otp")
    public ResponseEntity<HttpStatus> sentOpt(@RequestBody User username) throws JsonProcessingException {
        this.userService.sentOtp(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
