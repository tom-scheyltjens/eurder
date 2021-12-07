package com.switchfully.eurder.domain;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

public class Customer {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private final String emailAddress;
    private final String phoneNumber;
    private final List<Feature> featureList;

    public Customer(String firstName, String lastName, Address address, String emailAddress, String phoneNumber, @Nullable List<Feature> featureList) {

        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.featureList = featureList;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Address getAddress() {
        return address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isAbleTo(Feature feature){
        return false;
    }

}
