package com.switchfully.eurder.domain.user;

import java.util.List;

import static com.switchfully.eurder.domain.user.Feature.CREATE_ITEM;
import static com.switchfully.eurder.domain.user.Feature.VIEW_CUSTOMER;

public class Admin extends Customer {
    private static final List<Feature> FEATURES = List.of(CREATE_ITEM, VIEW_CUSTOMER);

    public Admin(String firstName, String lastName, Address address, String emailAddress, String phoneNumber) {
        super(firstName, lastName, address, emailAddress, phoneNumber);
        super.createAdmin();
    }



    @Override
    public boolean isAbleTo(Feature feature) {
        return FEATURES.contains(feature);
    }
}
