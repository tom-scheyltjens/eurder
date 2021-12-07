package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.domain.Feature;
import com.switchfully.eurder.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    private final CustomerRepository customerRepository;

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer validate(String authorization, Feature feature) {
        Customer customer = validateUserName(authorization);
        validateAccessToFeature(customer, feature);
        return customer;
    }

    private void validateAccessToFeature(Customer customer, Feature feature) {
        if (!customer.isAbleTo(feature)) throw new IllegalArgumentException();
    }

    public Customer validateUserName(String authorization) {
        String decodeUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        String email = decodeUsernameAndPassword.substring(0, decodeUsernameAndPassword.indexOf(":"));

        Customer customer = customerRepository.getByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException();
        }
        return customer;
    }
}
