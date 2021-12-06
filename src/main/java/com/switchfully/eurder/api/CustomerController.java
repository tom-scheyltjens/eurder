package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody CreateCustomerDto createCustomerDto) {
        Customer customer = customerMapper.createCustomerToCustomer(createCustomerDto);
        customerService.addCustomer(customer);
        return customerMapper.customerToCustomerDto(customer);
    }
}
