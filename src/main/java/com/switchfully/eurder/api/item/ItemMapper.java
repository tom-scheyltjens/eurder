package com.switchfully.eurder.api.item;

import com.switchfully.eurder.domain.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public Item createItemToItem(CreateItemDto createItemDto) {
        return new Item(createItemDto.name(), createItemDto.description(), createItemDto.price(), createItemDto.amount());
    }

    public ItemDto itemToItemDto(Item item){
        return new ItemDto(item.getName(), item.getDescription(), item.getPrice(), item.getAmount());
    }
}
