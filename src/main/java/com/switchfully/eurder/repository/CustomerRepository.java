package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.domain.Admin;
import com.switchfully.eurder.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepository {
    private final Map<String, Customer> customers;

    public CustomerRepository() {
        this.customers = new ConcurrentHashMap<>();
        createDefaultAdmin();
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public Customer getByEmail(String email) {
        return customers.values().stream()
                .filter(user -> user.getEmailAddress().equals(email))
                .findFirst().orElse(null);
    }

    public void createDefaultAdmin() {
        Admin admin = new Admin("Default", "Admin", new Address("Minstreet", "14", "2300", "Turnhout"), "default@admin.com", "0123456789");
        customers.put(admin.getId(), admin);
    }
}
