package com.switchfully.eurder.api.customer;

import com.switchfully.eurder.domain.user.Customer;
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
