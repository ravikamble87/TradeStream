package com.tradestream.usermanagementservice.service;

import com.tradestream.usermanagementservice.dto.RegistrationRequest;
import com.tradestream.usermanagementservice.entity.Role;
import com.tradestream.usermanagementservice.entity.RoleName;
import com.tradestream.usermanagementservice.entity.User;
import com.tradestream.usermanagementservice.entity.UserStatus;
import com.tradestream.usermanagementservice.exception.DuplicateEmailException;
import com.tradestream.usermanagementservice.exception.RoleNotFoundException;
import com.tradestream.usermanagementservice.exception.UserNotFoundException;
import com.tradestream.usermanagementservice.repository.RoleRepository;
import com.tradestream.usermanagementservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User register(RegistrationRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new DuplicateEmailException("A user with email " + request.email() + " already exists");
        }

        Role defaultRole = roleRepository.findByName(RoleName.TRADER)
                .orElseThrow(() -> new RoleNotFoundException("Default role TRADER is not configured"));

        User user = User.builder()
                .fullName(request.name())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .status(UserStatus.ACTIVE)
                .roles(new HashSet<>(Set.of(defaultRole)))
                .build();

        return userRepository.save(user);
    }

    public Optional<User> findById(UUID id){
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public User updateStatus(UUID id, UserStatus status){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with id: " + id));
        user.setStatus(status);
        return userRepository.save(user);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void deleteById(UUID id){
        userRepository.deleteById(id);
    }
}
