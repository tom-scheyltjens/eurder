package com.switchfully.eurder.api.customer;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateCustomerDto(@NotEmpty(message = "first name should be provided") String firstName,
                                @NotEmpty(message = "last name should be provided") String lastName,
                                @NotNull(message = "address should be provided") @Valid CreateAddressDto createAddressDto,
                                @NotEmpty(message = "email address  should be provided") String emailAddress,
                                @NotEmpty(message = "phone number should be provided") String phoneNumber) {
}
