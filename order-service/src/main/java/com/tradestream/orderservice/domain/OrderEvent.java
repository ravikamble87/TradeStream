package com.tradestream.orderservice.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderEvent(
        String orderId,
        String clientOrderId,
        String instrumentSymbol,
        OrderSide side,
        OrderType type,
        long quantity,
        BigDecimal limitPrice,
        Instant acceptedAt
) {
}
