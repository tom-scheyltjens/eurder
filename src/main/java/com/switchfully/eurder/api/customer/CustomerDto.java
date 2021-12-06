package com.switchfully.eurder.api.customer;

import com.switchfully.eurder.domain.Address;

public record CustomerDto(String firstName, String lastName, Address address, String emailAddress, String phoneNumber) {
}
