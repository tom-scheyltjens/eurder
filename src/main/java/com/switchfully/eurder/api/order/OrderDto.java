package com.switchfully.eurder.api.order;

import com.switchfully.eurder.domain.ItemGroup;

public record OrderDto(String id, ItemGroup itemGroup, double totalPrice) {
}
