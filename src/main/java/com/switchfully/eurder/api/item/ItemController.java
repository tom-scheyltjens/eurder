package com.switchfully.eurder.api.item;

import com.switchfully.eurder.api.customer.CustomerController;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.service.ItemService;
import com.switchfully.eurder.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static com.switchfully.eurder.domain.user.Feature.CREATE_ITEM;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/items")
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final SecurityService securityService;
    private final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    public ItemController(ItemService itemService, ItemMapper itemMapper, SecurityService securityService) {
        this.itemService = itemService;
        this.itemMapper = itemMapper;
        this.securityService = securityService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestHeader String authorization,@Valid @RequestBody CreateItemDto createItemDto) {
        securityService.validate(authorization, CREATE_ITEM);
        Item item = itemMapper.createItemToItem(createItemDto);
        itemService.addItem(item);
        return itemMapper.itemToItemDto(item);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            LOGGER.error(errorMessage, exception);
        });
        return errors;
    }
}
