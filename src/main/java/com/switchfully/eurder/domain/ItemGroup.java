package com.switchfully.eurder.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "item_groups")

public class ItemGroup {
    public static final int DEFAULT_SHIPPING_DATE = 1;

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "fk_item_id")
    private Item item;

    @Column(name = "item_price")
    private double itemPrice;

    @Column(name = "amount")
    private int amount;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @ManyToOne
    @JoinColumn(name = "fk_order_id")
    private Order order;

    protected ItemGroup() {
    }

    public ItemGroup(Item item, int amount, double itemPrice) {
        this.id = UUID.randomUUID().toString();
        this.item = item;
        this.itemPrice = itemPrice;
        this.amount = amount;
        this.shippingDate = LocalDate.now().plusDays(DEFAULT_SHIPPING_DATE);
    }

    public String getId() {
        return id;
    }

    public Item getItem() {
        return item;
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

    public void setOrder(Order order) {
        this.order = order;
    }
}
