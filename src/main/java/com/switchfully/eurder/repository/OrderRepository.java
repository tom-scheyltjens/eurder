package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepository {
    private final Map<String, Order> orders;

    public OrderRepository() {
        this.orders = new ConcurrentHashMap<>();
    }

    public void addOrder(Order order) {
        orders.put(order.getId(), order);
    }
}
