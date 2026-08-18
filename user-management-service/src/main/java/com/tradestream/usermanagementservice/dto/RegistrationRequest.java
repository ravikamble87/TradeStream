package com.tradestream.usermanagementservice.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistrationRequest(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password)
{}
