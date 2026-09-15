package com.tradestream.usermanagement.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record RegistrationResponse(
        UUID id,
        String username,
        String email,
        Instant createdAt,
        Instant updatedAt
) {}