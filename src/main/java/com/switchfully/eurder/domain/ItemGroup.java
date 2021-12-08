package com.switchfully.eurder.domain;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroup {
    public static final int DEFAULT_SHIPPING_DATE = 1;
    private final String id;
    private final String itemId;
    private final double itemPrice;
    private final int amount;
    private LocalDate shippingDate;

    public ItemGroup(String itemId, int amount, double itemPrice) {
        this.id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.amount = amount;
        this.shippingDate = LocalDate.now().plusDays(DEFAULT_SHIPPING_DATE);
    }

    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDate date) {
        this.shippingDate = date;
    }
}
