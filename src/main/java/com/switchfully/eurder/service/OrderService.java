package com.switchfully.eurder.service;

import com.switchfully.eurder.api.order.CreateOrderDto;
import com.switchfully.eurder.api.order.OrderDto;
import com.switchfully.eurder.api.order.OrderMapper;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.ItemGroup;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.repository.ItemRepository;
import com.switchfully.eurder.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDto addOrder(CreateOrderDto createOrderDto) {
        ItemGroup itemGroup = getItemGroup(createOrderDto);
        Order order = new Order(createOrderDto.customerId(), itemGroup);
        orderRepository.addOrder(order);
        return orderMapper.orderToOrderDto(order);
    }

    private ItemGroup getItemGroup(CreateOrderDto createOrderDto) {
        Item orderedItem = itemRepository.getItem(createOrderDto.itemId());
        //setShippingDate (or in domain?)
        return new ItemGroup(orderedItem.getId(), createOrderDto.amount(), orderedItem.getPrice());
    }
}
