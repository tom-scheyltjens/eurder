package com.switchfully.eurder.service;

import com.switchfully.eurder.api.order.CreateOrderDto;
import com.switchfully.eurder.api.order.ItemGroupDto;
import com.switchfully.eurder.api.order.OrderDto;
import com.switchfully.eurder.api.order.OrderMapper;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.ItemGroup;
import com.switchfully.eurder.domain.Order;
import com.switchfully.eurder.domain.exception.UnknownIdException;
import com.switchfully.eurder.repository.ItemRepository;
import com.switchfully.eurder.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        List<ItemGroup> itemGroups = getItemGroups(createOrderDto.itemGroupDtos());
        Order order = new Order(createOrderDto.customerId(), itemGroups);
        orderRepository.addOrder(order);
        return orderMapper.orderToOrderDto(order);
    }

    private List<ItemGroup> getItemGroups(List<ItemGroupDto> itemGroupDtos) {
        List<ItemGroup> itemGroups = new ArrayList<>();
        for (ItemGroupDto itemGroupDto : itemGroupDtos){
            validateItemId(itemGroupDto.itemId());
            Item orderedItem = itemRepository.getItem(itemGroupDto.itemId());
            ItemGroup itemGroup = new ItemGroup(itemGroupDto.itemId(), itemGroupDto.amount(), orderedItem.getPrice());

            setShippingDate(itemGroupDto.amount(), orderedItem, itemGroup);
            removeOrderItemsFromAmount(itemGroupDto.amount(), orderedItem);

            itemGroups.add(itemGroup);
        }
        return itemGroups;
    }

    private void removeOrderItemsFromAmount(int createOrderAmount, Item orderedItem) {
        orderedItem.setAmount(createOrderAmount);
    }

    private void setShippingDate(int createOrderAmount, Item orderedItem, ItemGroup itemGroup) {
        if (orderedItem.getAmount() < createOrderAmount)
            itemGroup.setShippingDate(LocalDate.now().plusDays(DAYS_TO_ADD_IF_ITEM_IS_UNAVAILABLE));
    }

    private void validateItemId(String itemId){
        if (itemRepository.getItem(itemId) == null) throw new UnknownIdException(itemId + " is not recognized in our system");
    }
}
