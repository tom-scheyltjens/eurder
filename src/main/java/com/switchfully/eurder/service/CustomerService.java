package com.switchfully.eurder.service;

import com.switchfully.eurder.api.customer.CreateCustomerDto;
import com.switchfully.eurder.api.customer.CustomerDto;
import com.switchfully.eurder.api.customer.CustomerMapper;
import com.switchfully.eurder.domain.exception.InvalidEmailAddressException;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto addCustomer(CreateCustomerDto createCustomerDto) {
        assertEmailIsUnique(createCustomerDto.emailAddress());
        Customer customer = customerMapper.createCustomerToCustomer(createCustomerDto);
        customerRepository.addCustomer(customer);
        return customerMapper.customerToCustomerDto(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.getAllCustomers().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomer(String id) {
        return customerMapper.customerToCustomerDto(customerRepository.getCustomer(id));
    }

    private void assertEmailIsUnique(String emailAddress) {
        for(Customer customer : customerRepository.getAllUsers()) {
            if (customer.getEmailAddress().equals(emailAddress)){
                throw new InvalidEmailAddressException(emailAddress + " is already in use, please provide an other email address");
            }
        }
    }
}
