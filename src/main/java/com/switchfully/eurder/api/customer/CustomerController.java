package com.switchfully.eurder.api.customer;

import com.switchfully.eurder.domain.Customer;
import com.switchfully.eurder.domain.Feature;
import com.switchfully.eurder.service.CustomerService;
import com.switchfully.eurder.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.switchfully.eurder.domain.Feature.VIEW_CUSTOMER;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final SecurityService securityService;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper, SecurityService securityService) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
        this.securityService = securityService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDto> getCustomers(@RequestHeader String authorization) {
        securityService.validate(authorization, VIEW_CUSTOMER);
        return customerService.getAllCustomers();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
        Customer customer = customerMapper.createCustomerToCustomer(createCustomerDto);
        customerService.addCustomer(customer);
        return customerMapper.customerToCustomerDto(customer);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            LOGGER.error(errorMessage, exception);
        });
        return errors;
    }
}
