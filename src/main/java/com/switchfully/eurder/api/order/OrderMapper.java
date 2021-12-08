package com.switchfully.eurder.api.order;

import com.switchfully.eurder.domain.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderDto orderToOrderDto(Order order) {
        return new OrderDto(order.getId(), order.getItemGroups(), order.getTotalPrice());
    }
}
