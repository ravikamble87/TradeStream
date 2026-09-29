package com.tradestream.usermanagement.controller;

import com.tradestream.usermanagement.dto.LoginRequest;
import com.tradestream.usermanagement.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.userName(), req.password()));
        String token = jwtUtil.generateToken(req.userName());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
