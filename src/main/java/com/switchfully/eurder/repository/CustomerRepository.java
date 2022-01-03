package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.user.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void addCustomer(Customer customer) {
        System.out.println(customer.getFirstName() + " " + customer.getId()); //only for debugging in postman
        entityManager.persist(customer);
    }

    public Customer getByEmail(String email) {
        String sql = "SELECT c FROM Customer c WHERE c.emailAddress = :email";
        return entityManager.createQuery(sql, Customer.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    public List<Customer> getAllUsers() {
        String sql = "SELECT c FROM Customer c";
        return entityManager.createQuery(sql, Customer.class)
                .getResultList();
    }


    public List<Customer> getAllCustomers() {
        String sql = "SELECT c FROM Customer c WHERE c.isAdmin = false";
        return entityManager.createQuery(sql, Customer.class)
                .getResultList();
    }

    public Customer getCustomer(String id) {
        String sql = "SELECT c FROM Customer c WHERE c.id = :id";
        return entityManager.createQuery(sql, Customer.class)
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst()
                .orElse(null);
    }

}
