package com.switchfully.eurder.api;

import com.switchfully.eurder.Utility;
import com.switchfully.eurder.api.item.CreateItemDto;
import com.switchfully.eurder.api.item.ItemDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ItemControllerTest {
    @Value("${server.port}")
    private int port;

    @Test
    void createItem_givenAnItemToCreate_thenTheNewlyCreatedItemIsSavedAndReturned() {
        CreateItemDto createItemDto = new CreateItemDto("Item", "the first item", 14.7, 3);

        ItemDto itemDto =
                RestAssured
                        .given()
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("default@admin.com", "123"))
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(ItemDto.class);


        assertThat(itemDto.name()).isEqualTo(createItemDto.name());
        assertThat(itemDto.description()).isEqualTo(createItemDto.description());
        assertThat(itemDto.price()).isEqualTo(createItemDto.price());
        assertThat(itemDto.amount()).isEqualTo(createItemDto.amount());
    }
}
