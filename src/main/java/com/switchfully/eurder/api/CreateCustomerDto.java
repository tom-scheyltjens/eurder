package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.Address;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateCustomerDto(@NotEmpty(message = "first name should be provided") String firstName,
                                @NotEmpty(message = "last name should be provided") String lastName,
                                @NotNull(message = "address should be provided") Address address,
                                @NotEmpty(message = "email address  should be provided")String emailAddress,
                                @NotEmpty(message = "phone number should be provided") String phoneNumber) {
}
