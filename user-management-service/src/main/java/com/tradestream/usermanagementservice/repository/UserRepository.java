package com.tradestream.usermanagementservice.repository;

import com.tradestream.usermanagementservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Roles are fetched eagerly here on purpose: this is the lookup JwtAuthFilter uses
    // on every authenticated request, and that filter runs before Spring's
    // open-session-in-view interceptor binds a session (OSIV only wraps the MVC handler
    // phase, not the raw servlet filter chain) - a lazy collection would blow up with
    // LazyInitializationException once accessed outside a session.
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);
}
