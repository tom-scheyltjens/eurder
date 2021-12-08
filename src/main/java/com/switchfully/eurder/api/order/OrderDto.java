package com.switchfully.eurder.api.order;

import com.switchfully.eurder.domain.ItemGroup;

import java.util.List;

public record OrderDto(String id, List<ItemGroup> itemGroups, double totalPrice) {
}
