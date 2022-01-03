package com.switchfully.eurder.api;

import com.switchfully.eurder.Utility;
import com.switchfully.eurder.api.item.CreateItemDto;
import com.switchfully.eurder.api.item.ItemDto;
import com.switchfully.eurder.domain.user.Address;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.repository.CustomerRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ItemControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        Customer customer = new Customer("Tom", "Tomsk", new Address("street", "14", "2300", "Turnhout"), "tom@tomsk.com", "0123456789");
        customerRepository.addCustomer(customer);
    }

    @Test
    void createItem_givenAnItemToCreateWithAdminAccess_thenTheNewlyCreatedItemIsSavedAndReturned() {
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

    @Test
    void createItem_givenAnItemToCreateWithoutAdminAccess_thenAnUnauthorizedExceptionIsThrown() {
        CreateItemDto createItemDto = new CreateItemDto("Item", "the first item", 14.7, 3);

        String message =
                RestAssured
                        .given()
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("tom@tomsk.com", "123"))
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .extract()
                        .path("message");

        assertThat(message).isEqualTo("tom@tomsk.com does not have access to CREATE_ITEM");
    }

    @Test
    void createItem_givenAnItemToCreateWithAnUnknownUser_thenAnUnauthorizedUserExceptionIsThrown() {
        CreateItemDto createItemDto = new CreateItemDto("Item", "the first item", 14.7, 3);

        String message =
                RestAssured
                        .given()
                        .body(createItemDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("unknown@user.com", "123"))
                        .when()
                        .port(port)
                        .post("/items")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .extract()
                        .path("message");

        assertThat(message).isEqualTo("unknown@user.com is not recognized in our system.");
    }

    @Test
    void createItem_givenAnItemToCreateWithANegativeAmountAndAdminAccess_thenAnErrorIsReturned() {
        CreateItemDto createItemDto = new CreateItemDto("Item", "the first item", 14.7, -2);


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
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
