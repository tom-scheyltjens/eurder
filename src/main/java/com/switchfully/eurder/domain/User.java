package com.switchfully.eurder.domain;

import java.util.UUID;

public class User {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final String emailAddress;
    private final String phoneNumber;

    public User(String firstName, String lastName, Address address, String emailAddress, String phoneNumber) {

        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
    }
}
