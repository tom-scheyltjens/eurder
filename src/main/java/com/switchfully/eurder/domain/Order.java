package com.switchfully.eurder.domain;

import com.switchfully.eurder.domain.user.Customer;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")

public class Order {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "fk_customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<ItemGroup> itemGroups;

    @Column(name = "total_price")
    private double totalPrice;

    protected Order() {}
    public Order(Customer customer, List <ItemGroup> itemGroups) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
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
