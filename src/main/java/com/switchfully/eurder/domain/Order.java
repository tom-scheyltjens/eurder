package com.switchfully.eurder.domain;

import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final String customerId;
    private final List<ItemGroup> itemGroups;
    private double totalPrice;

    public Order(String customerId, List <ItemGroup> itemGroups) {
        this.id = UUID.randomUUID().toString();
        this.customerId = customerId;
        this.itemGroups = itemGroups;
        setTotalPrice();
    }

    public String getId() {
        return id;
    }

    public List<ItemGroup> getItemGroups() {
        return itemGroups;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice() {
        double price = 0;
        for (ItemGroup itemGroup : itemGroups) {
            price += itemGroup.getItemPrice() * itemGroup.getAmount();
        }
        this.totalPrice = price;
    }
}
