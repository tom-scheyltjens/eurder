package com.switchfully.eurder.api.item;

public record CreateItemDto(String name, String description, double price, int amount) {
}
