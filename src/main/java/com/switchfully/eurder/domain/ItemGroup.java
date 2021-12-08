package com.switchfully.eurder.domain;

import java.time.LocalDate;

public class ItemGroup {
    private final String itemId;
    private final double itemPrice;
    private final int amount;
    private final LocalDate shippingDate;

    public ItemGroup(String itemId, double itemPrice, int amount, LocalDate shippingDate) {
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.amount = amount;
        this.shippingDate = shippingDate;
    }
}
