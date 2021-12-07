package com.switchfully.eurder.domain.user;

import javax.validation.constraints.NotEmpty;

public record Address(@NotEmpty(message = "street name should be provided")String streetName,
                      @NotEmpty(message = "house number should be provided")String houseNumber,
                      @NotEmpty(message = "postal code should be provided")String postalCode,
                      @NotEmpty(message = "city should be provided")String city) {

}
