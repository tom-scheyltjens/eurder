package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepository {
    private final Map<String, Customer> customers;

    public CustomerRepository() {
        this.customers = new ConcurrentHashMap<>();
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public Customer getByEmail(String email) {
        return customers.values().stream()
                .filter(user -> user.getEmailAddress().equals(email))
                .findFirst().orElse(null);
    }
}
