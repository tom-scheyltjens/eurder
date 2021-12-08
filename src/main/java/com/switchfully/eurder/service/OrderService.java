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

import java.time.LocalDate;

@Service
public class OrderService {
    public static final int DAYS_TO_ADD_IF_ITEM_IS_UNAVAILABLE = 7;
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
        ItemGroup itemGroup =  new ItemGroup(orderedItem.getId(), createOrderDto.amount(), orderedItem.getPrice());

        setShippingDate(createOrderDto, orderedItem, itemGroup);

        removeOrderItemsFromAmount(createOrderDto, orderedItem);

        return itemGroup;
    }

    private void removeOrderItemsFromAmount(CreateOrderDto createOrderDto, Item orderedItem) {
        orderedItem.setAmount(createOrderDto.amount());
    }

    private void setShippingDate(CreateOrderDto createOrderDto, Item orderedItem, ItemGroup itemGroup) {
        if (orderedItem.getAmount() < createOrderDto.amount())
            itemGroup.setShippingDate(LocalDate.now().plusDays(DAYS_TO_ADD_IF_ITEM_IS_UNAVAILABLE));
    }
}
