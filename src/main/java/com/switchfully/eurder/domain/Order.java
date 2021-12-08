package com.switchfully.eurder.domain;

import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final ItemGroup itemGroup;
    private double totalPrice;

    public Order(String customerId, ItemGroup itemGroup) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroup = itemGroup;
        setTotalPrice();
    }

    public String getId() {
        return id;
    }

    public ItemGroup getItemGroup() {
        return itemGroup;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = itemGroup.getItemPrice() * itemGroup.getAmount();
    }
}
