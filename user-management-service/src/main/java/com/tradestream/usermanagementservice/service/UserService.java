package com.tradestream.usermanagementservice.service;

import com.tradestream.usermanagementservice.entity.User;
import com.tradestream.usermanagementservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findById(UUID id){
        return userRepository.findById(id);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void deleteById(UUID id){
        userRepository.deleteById(id);
    }
}
