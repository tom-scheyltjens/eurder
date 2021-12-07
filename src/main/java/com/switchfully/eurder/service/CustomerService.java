package com.switchfully.eurder.service;

import com.switchfully.eurder.api.customer.CustomerDto;
import com.switchfully.eurder.api.customer.CustomerMapper;
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

    public void addCustomer(Customer customer) {
        customerRepository.addCustomer(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.getAllCustomers().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    public CustomerDto getCustomer(String id) {
        return customerMapper.customerToCustomerDto(customerRepository.getCustomer(id));
    }
}
