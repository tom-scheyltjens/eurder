package com.switchfully.eurder.api.order;

public record CreateOrderDto(String customerId, String itemId, int amount) {
}
