package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.user.Address;
import com.switchfully.eurder.domain.user.Admin;
import com.switchfully.eurder.domain.user.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class CustomerRepository {
    private final Map<String, Customer> customers;

    public CustomerRepository() {
        this.customers = new ConcurrentHashMap<>();
        createDefaultAdmin();
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
        System.out.println(customer.getFirstName() + " " + customer.getId()); //only for debugging in postman
    }

    public Customer getByEmail(String email) {
        return customers.values().stream()
                .filter(user -> user.getEmailAddress().equals(email))
                .findFirst().orElse(null);
    }

    public void createDefaultAdmin() {
        Admin admin = new Admin("Default", "Admin", new Address("street", "14", "2300", "Turnhout"), "default@admin.com", "0123456789");
        customers.put(admin.getId(), admin);
    }

    public List<Customer> getAllUsers() {
        return customers.values().stream().toList();
    }

    public List<Customer> getAllCustomers() {
        return customers.values().stream()
                .filter(customer -> customer.getClass().equals(Customer.class))
                .collect(Collectors.toList());
    }

    public Customer getCustomer(String id){
        return customers.get(id);
    }
}
