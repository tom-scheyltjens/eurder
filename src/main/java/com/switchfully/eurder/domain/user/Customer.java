package com.switchfully.eurder.domain.user;

import com.switchfully.eurder.domain.exception.InvalidEmailAddressException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import java.util.UUID;

public class Customer {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final Address address;
    private String emailAddress;
    private final String phoneNumber;

    public Customer(String firstName, String lastName, Address address, String emailAddress, String phoneNumber) {

        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        setEmailAddress(emailAddress);
        this.phoneNumber = phoneNumber;
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

    private void setEmailAddress(String emailAddress) {
        if (!isValidEmailAddress(emailAddress)) {
            throw new InvalidEmailAddressException(emailAddress + " is not a valid email address");
        }
        this.emailAddress = emailAddress;
    }

    private boolean isValidEmailAddress(String emailAddress){
        try {
            InternetAddress email = new InternetAddress(emailAddress);
            email.validate();
        } catch (AddressException exception) {
            return false;
        }
        return true;
    }

    public boolean isAbleTo(Feature feature) {
        return false;
    }

}
