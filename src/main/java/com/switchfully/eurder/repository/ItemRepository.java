package com.switchfully.eurder.repository;

import com.switchfully.eurder.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {
    private final Map<String, Item> items;

    public ItemRepository() {
        this.items = new ConcurrentHashMap<>();
    }

    public void addItem(Item item) { items.put(item.getId(), item); }
}
