package com.switchfully.eurder.api.item;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record CreateItemDto(@NotEmpty(message = "name should be provided") String name,
                            @NotEmpty(message = "description should be provided") String description,
                            @Min(value = 0, message = "price should be greater then 0") @NotNull(message = "price should be provided") Double price,
                            @Min(value = 1, message = "amount should be bigger then 0") @NotNull(message = "amount should be provided") Integer amount) {
}
