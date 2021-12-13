package com.switchfully.eurder.api.order;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public record CreateOrderDto(@NotEmpty(message = "customer id should be provided") String customerId,
                             @NotEmpty(message = "item should be provided") List<@Valid ItemGroupDto> itemGroupDtos) {
}
