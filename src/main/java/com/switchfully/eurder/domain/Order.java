package com.switchfully.eurder.domain;

import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final ItemGroup itemGroup;

    public Order(String customerId, ItemGroup itemGroup) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroup = itemGroup;
    }
}
