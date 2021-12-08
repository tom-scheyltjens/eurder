package com.switchfully.eurder.domain;

public class Order {
    private final String customerId;
    private final ItemGroup itemGroup;

    public Order(String customerId, ItemGroup itemGroup) {
        this.customerId = customerId;
        this.itemGroup = itemGroup;
    }
}
