package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer createCustomerToCustomer(CreateCustomerDto createCustomerDto) {
        return new Customer(createCustomerDto.firstName(), createCustomerDto.lastName(), createCustomerDto.address(), createCustomerDto.emailAddress(), createCustomerDto.phoneNumber());
    }

    public CustomerDto customerToCustomerDto(Customer customer) {
        return new CustomerDto(customer.getFirstName(), customer.getLastName(), customer.getAddress(), customer.getEmailAddress(), customer.getPhoneNumber());
    }
}
