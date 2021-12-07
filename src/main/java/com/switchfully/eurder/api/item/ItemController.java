package com.switchfully.eurder.api.item;

import com.switchfully.eurder.domain.Feature;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.service.ItemService;
import com.switchfully.eurder.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.switchfully.eurder.domain.Feature.CREATE_ITEM;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final SecurityService securityService;

    public ItemController(ItemService itemService, ItemMapper itemMapper, SecurityService securityService) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.securityService = securityService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestHeader String authorization, @RequestBody CreateItemDto createItemDto) {
        securityService.validate(authorization, CREATE_ITEM);
        Item item = itemMapper.createItemToItem(createItemDto);
        itemService.addItem(item);
        return itemMapper.itemToItemDto(item);
    }
}
