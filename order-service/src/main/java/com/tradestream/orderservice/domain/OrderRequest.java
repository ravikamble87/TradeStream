package com.tradestream.orderservice.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderRequest(
        @NotBlank String instrumentSymbol,
        @NotNull OrderSide side,
        @NotNull OrderType type,
        @Positive long quantity,
        @DecimalMin(value = "0.0", inclusive = false) BigDecimal limitPrice,
        @NotBlank String clientOrderId
) {
    public OrderEvent toEvent() {
        return new OrderEvent(
                UUID.randomUUID().toString(),
                clientOrderId,
                instrumentSymbol,
                side,
                type,
                quantity,
                limitPrice,
                Instant.now()
        );
    }
}
