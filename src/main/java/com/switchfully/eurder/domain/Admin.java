package com.switchfully.eurder.domain;

import java.util.List;

import static com.switchfully.eurder.domain.Feature.CREATE_ITEM;

public class Admin extends Customer {
    private static final List<Feature> FEATURES = List.of(CREATE_ITEM);

    public Admin(String firstName, String lastName, Address address, String emailAddress, String phoneNumber) {
        super(firstName, lastName, address, emailAddress, phoneNumber, FEATURES);
    }

    @Override
    public boolean isAbleTo(Feature feature) {
        return FEATURES.contains(feature);
    }
}
