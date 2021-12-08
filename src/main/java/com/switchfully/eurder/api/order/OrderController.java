package com.switchfully.eurder.api.order;

import com.switchfully.eurder.service.OrderService;
import com.switchfully.eurder.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {
    private final OrderService orderService;
    private final SecurityService securityService;

    public OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestHeader String authorization, @Valid @RequestBody CreateOrderDto createOrderDto) {
        securityService.authenticate(authorization, createOrderDto.customerId());
        return orderService.addOrder(createOrderDto);
    }
}
