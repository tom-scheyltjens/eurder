package com.switchfully.eurder.api.item;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.repository.ItemRepository;
import com.switchfully.eurder.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    public ItemController(ItemService itemService, ItemMapper itemMapper) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestBody CreateItemDto createItemDto) {
        Item item = itemMapper.createItemToItem(createItemDto);
        itemService.addItem(item);
        return itemMapper.itemToItemDto(item);
    }
}
