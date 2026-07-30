package com.tradestream.usermanagementservice.repository;

import com.tradestream.usermanagementservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
