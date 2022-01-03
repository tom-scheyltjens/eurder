package com.switchfully.eurder.domain.user;

import com.switchfully.eurder.domain.exception.InvalidEmailAddressException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "customers")

public class Customer {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Embedded
    private Address address;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_admin")
    private boolean isAdmin;

    protected Customer(){}

    public Customer(String firstName, String lastName, Address address, String emailAddress, String phoneNumber) {

        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        setEmailAddress(emailAddress);
        this.phoneNumber = phoneNumber;
        this.isAdmin = false;
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

    protected void createAdmin() {
        this.isAdmin = true;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isAbleTo(Feature feature) {
        return false;
    }

}
