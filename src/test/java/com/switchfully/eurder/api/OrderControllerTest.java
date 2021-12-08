package com.switchfully.eurder.api;

import com.switchfully.eurder.Utility;
import com.switchfully.eurder.api.order.CreateOrderDto;
import com.switchfully.eurder.api.order.OrderDto;
import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.domain.ItemGroup;
import com.switchfully.eurder.domain.user.Address;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.repository.CustomerRepository;
import com.switchfully.eurder.repository.ItemRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerTest {
    @Value("${server.port}")
    private int port;
    private Customer firstShopper;
    private Item firstItem;
    private Item secondItem;
    private Item thirdItem;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderControllerTest(CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    @BeforeAll
    public void setUp() {
        firstShopper = new Customer("First", "Shopper", new Address("street", "14", "2300", "Turnhout"), "first@shopper.com", "9876543210");
        customerRepository.addCustomer(firstShopper);
        firstItem = new Item("First Item", "The first item to order", 17.3, 999);
        itemRepository.addItem(firstItem);
        secondItem = new Item("Second Item", "The second item to order", 14.7, 3);
        itemRepository.addItem(secondItem);
        thirdItem = new Item("Third Item", "The third item to order", 9.99, 999);
        itemRepository.addItem(thirdItem);
    }

    @Test
    void createOrder_givenACorrectCreateOrderDtoAndCredentials_thenTheOrderIsCreatedAndReturned() {
        CreateOrderDto createOrderDto = new CreateOrderDto(firstShopper.getId(), List.of(firstItem.getId()), List.of(3));

        OrderDto orderDto =
                RestAssured
                        .given()
                        .body(createOrderDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization(firstShopper.getEmailAddress(), "123"))
                        .when()
                        .port(port)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(OrderDto.class);

        assertThat(orderDto.totalPrice()).isEqualTo(17.3 * 3);
        assertThat(orderDto.itemGroups().size()).isEqualTo(1);
        ItemGroup itemGroup = orderDto.itemGroups().get(0);
        assertThat(itemGroup.getItemId()).isEqualTo(firstItem.getId());
    }

    @Test
    void createOrder_givenAnAmountThatIsNotInStock_thenTheOrderIsCreateWithShippingDateSevenDaysFromNow() {
        CreateOrderDto createOrderDto = new CreateOrderDto(firstShopper.getId(), List.of(secondItem.getId()), List.of(4));

        OrderDto orderDto =
                RestAssured
                        .given()
                        .body(createOrderDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization(firstShopper.getEmailAddress(), "123"))
                        .when()
                        .port(port)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(OrderDto.class);

        assertThat(orderDto.itemGroups().size()).isEqualTo(1);
        assertThat(orderDto.totalPrice()).isEqualTo(14.7 * 4);
        ItemGroup itemGroup = orderDto.itemGroups().get(0);
        assertThat(itemGroup.getItemId()).isEqualTo(secondItem.getId());
        assertThat(itemGroup.getShippingDate()).isEqualTo(LocalDate.now().plusDays(7));
        assertThat(secondItem.getAmount()).isEqualTo(0);
    }

    @Test
    void createOrder_givenAnIncorrectCustomerId_thenBadRequestErrorIsThrown() {
        CreateOrderDto createOrderDto = new CreateOrderDto("incorrectCustomerId", List.of(secondItem.getId()), List.of(4));

        RestAssured
                .given()
                .body(createOrderDto)
                .accept(JSON)
                .contentType(JSON)
                .header("Authorization", Utility.generateBase64Authorization(firstShopper.getEmailAddress(), "123"))
                .when()
                .port(port)
                .post("/orders")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void createOrder_givenACorrectCreateOrderWithTwoItemsDtoAndCredentials_thenTheOrderIsCreatedAndReturned() {
        CreateOrderDto createOrderDto = new CreateOrderDto(firstShopper.getId(), List.of(firstItem.getId(), thirdItem.getId()), List.of(3, 10));

        OrderDto orderDto =
                RestAssured
                        .given()
                        .body(createOrderDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization(firstShopper.getEmailAddress(), "123"))
                        .when()
                        .port(port)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(OrderDto.class);

        assertThat(orderDto.totalPrice()).isEqualTo(17.3 * 3 + 9.99 * 10);
        assertThat(orderDto.itemGroups().size()).isEqualTo(2);
    }
    @Test
    void createOrder_givenACorrectCreateOrderWithFourItemsDtoAndCredentials_thenTheOrderIsCreatedAndReturned() {
        Item fourthItem = new Item("4", "44", 147, 999);
        itemRepository.addItem(fourthItem);
        Item fifthItem = new Item("5", "55", 3.7, 999);
        itemRepository.addItem(fifthItem);
        CreateOrderDto createOrderDto = new CreateOrderDto(firstShopper.getId(), List.of(firstItem.getId(), thirdItem.getId(), fourthItem.getId(), fifthItem.getId()), List.of(3, 10, 2, 250));

        OrderDto orderDto =
                RestAssured
                        .given()
                        .body(createOrderDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization(firstShopper.getEmailAddress(), "123"))
                        .when()
                        .port(port)
                        .post("/orders")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(OrderDto.class);

        assertThat(orderDto.totalPrice()).isEqualTo(17.3 * 3 + 9.99 * 10 + 147 * 2 + 3.7 * 250);
        assertThat(orderDto.itemGroups().size()).isEqualTo(4);
    }
}
