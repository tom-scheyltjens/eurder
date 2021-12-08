package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.exception.UnauthorizedException;
import com.switchfully.eurder.domain.exception.UnknownIdException;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.domain.user.Feature;
import com.switchfully.eurder.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SecurityService {
    private final CustomerRepository customerRepository;

    public SecurityService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void validate(String authorization, Feature feature) {
        Customer customer = validateUserName(authorization);
        validateAccessToFeature(customer, feature);
    }

    private void validateAccessToFeature(Customer customer, Feature feature) {
        if (!customer.isAbleTo(feature))
            throw new UnauthorizedException(customer.getEmailAddress() + " does not have access to " + feature.name());
    }

    public Customer validateUserName(String authorization) {
        String email = getDecodedEmail(authorization);

        Customer customer = customerRepository.getByEmail(email);
        if (customer == null) {
            throw new UnauthorizedException(email + " is not recognized in our system.");
        }
        return customer;
    }

    private String getDecodedEmail(String authorization) {
        String decodeUsernameAndPassword = new String(Base64.getDecoder().decode(authorization.substring("Basic ".length())));
        return decodeUsernameAndPassword.substring(0, decodeUsernameAndPassword.indexOf(":"));
    }

    public void authenticate(String authentication, String customerId) {
        validateCustomerId(customerId);
        Customer customer = validateUserName(authentication);
        if (!customer.getId().equals(customerId))
            throw new UnauthorizedException("you can not order items for another customer");
    }

    private void validateCustomerId(String customerId) {
        if (customerRepository.getCustomer(customerId) == null)
            throw new UnknownIdException("the customer id provided is not recognized");
    }

}
