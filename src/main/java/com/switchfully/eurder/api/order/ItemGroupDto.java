package com.switchfully.eurder.api.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record ItemGroupDto(@NotEmpty(message = "itemId should be provided")String itemId,
                           @Min(value = 1, message = "amount should be bigger then 0") @NotNull(message = "amount should be provided") Integer amount) {
}
