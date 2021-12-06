package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.Address;

public record CreateCustomerDto(String firstName, String lastName, Address address, String emailAddress, String phoneNumber) {
}
