package com.tradestream.usermanagementservice.controller;

import com.tradestream.usermanagementservice.dto.RegistrationRequest;
import com.tradestream.usermanagementservice.dto.UserResponse;
import com.tradestream.usermanagementservice.entity.User;
import com.tradestream.usermanagementservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class UserRegistrationController {
    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("registerUser")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest) {
        User user = userService.register(registrationRequest);
        return new ResponseEntity<>(UserResponse.from(user), HttpStatus.CREATED);
    }
}
