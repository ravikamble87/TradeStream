package com.tradestream.usermanagement.service;

import com.tradestream.usermanagement.dto.RegistrationRequest;
import com.tradestream.usermanagement.dto.RegistrationResponse;
import com.tradestream.usermanagement.entity.User;
import com.tradestream.usermanagement.exception.EmailAlreadyExistsException;
import com.tradestream.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<RegistrationResponse> registerUser(RegistrationRequest registrationRequest) {

        if (userRepository.existsByEmail(registrationRequest.email())) {
            throw new EmailAlreadyExistsException(registrationRequest.email());
        }

        User user = User.builder()
                .username(registrationRequest.userName())
                .email(registrationRequest.email())
                .passwordHash(passwordEncoder.encode(registrationRequest.password()))
                .build();

        User savedUser = userRepository.save(user);

        RegistrationResponse registrationResponse = RegistrationResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .username(savedUser.getUsername())
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .build();

        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
    }
}