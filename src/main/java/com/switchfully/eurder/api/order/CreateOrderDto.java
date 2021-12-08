package com.switchfully.eurder.api.order;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderDto(@NotEmpty(message = "customer id should be provided")String customerId,
                             @NotEmpty(message = "description should be provided") List<String> itemId,
                             @NotNull(message = "amount should be provided") List<Integer> amount) {
}

//@Min(value = 1, message = "amount should be bigger then 0")
