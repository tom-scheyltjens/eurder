package com.switchfully.eurder.domain;

import java.time.LocalDate;
import java.util.UUID;

public class ItemGroup {
    private final String id;
    private final String itemId;
    private final double itemPrice;
    private final int amount;
    private final LocalDate shippingDate;

    public ItemGroup(String itemId, double itemPrice, int amount, LocalDate shippingDate) {
        this.id = UUID.randomUUID().toString();
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.amount = amount;
        this.shippingDate = shippingDate;
    }
}
