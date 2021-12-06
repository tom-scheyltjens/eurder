package com.switchfully.eurder.service;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void addItem(Item item) {
        itemRepository.addItem(item);
    }
}
