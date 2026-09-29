package com.tradestream.usermanagement.service;

import com.tradestream.usermanagement.dto.LoginRequest;
import com.tradestream.usermanagement.dto.LoginResponse;
import com.tradestream.usermanagement.dto.RegistrationRequest;
import com.tradestream.usermanagement.dto.RegistrationResponse;
import com.tradestream.usermanagement.entity.User;
import com.tradestream.usermanagement.exception.EmailAlreadyExistsException;
import com.tradestream.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<RegistrationResponse> registerUser(RegistrationRequest registrationRequest) {

        if (userRepository.existsByEmail(registrationRequest.email())) {
            throw new EmailAlreadyExistsException(registrationRequest.email());
        }

        User user = User.builder()
                .username(registrationRequest.userName())
                .email(registrationRequest.email())
                .passwordHash(passwordEncoder.encode(registrationRequest.password()))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .lastLoginAt(Instant.now())
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

    @Transactional
    public ResponseEntity<LoginResponse>  loginUser(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet()))
                .build();
    }
}