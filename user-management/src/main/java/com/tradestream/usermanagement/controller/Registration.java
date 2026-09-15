package com.tradestream.usermanagement.controller;

import com.tradestream.usermanagement.dto.RegistrationRequest;
import com.tradestream.usermanagement.dto.RegistrationResponse;
import com.tradestream.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class Registration {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest registrationRequest) {
        return userService.registerUser(registrationRequest);
    }
}
