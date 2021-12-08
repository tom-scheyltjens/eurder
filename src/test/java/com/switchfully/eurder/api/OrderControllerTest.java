package com.switchfully.eurder.api;

import com.switchfully.eurder.Utility;
import com.switchfully.eurder.api.order.CreateOrderDto;
import com.switchfully.eurder.api.order.OrderDto;
import com.switchfully.eurder.domain.Item;
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
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public OrderControllerTest(CustomerRepository customerRepository, ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
    }

    @BeforeAll
    public void setUp(){
        firstShopper = new Customer("First", "Shopper", new Address("street", "14", "2300", "Turnhout"), "first@shopper.com", "9876543210");
        customerRepository.addCustomer(firstShopper);
        firstItem = new Item("First Item", "The first item to order", 17.3, 999);
        itemRepository.addItem(firstItem);
        secondItem = new Item("Second Item", "The second item to order", 14.7, 3);
        itemRepository.addItem(secondItem);
    }

    @Test
    void createOrder_givenACorrectCreateOrderDtoAndCredentials_thenTheOrderIsCreatedAndReturned(){
        CreateOrderDto createOrderDto = new CreateOrderDto(firstShopper.getId(), firstItem.getId(), 3);

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

        assertThat(orderDto.itemGroup().getItemId()).isEqualTo(firstItem.getId());
        assertThat(orderDto.totalPrice()).isEqualTo(17.3 * 3);
    }

    @Test
    void createOrder_givenAnAmountThatIsNotInStock_thenTheOrderIsCreateWithShippingDateSevenDaysFromNow(){
        CreateOrderDto createOrderDto = new CreateOrderDto(firstShopper.getId(), secondItem.getId(), 4);

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

        assertThat(orderDto.itemGroup().getItemId()).isEqualTo(secondItem.getId());
        assertThat(orderDto.totalPrice()).isEqualTo(14.7 * 4);
        assertThat(orderDto.itemGroup().getShippingDate()).isEqualTo(LocalDate.now().plusDays(7));
        assertThat(secondItem.getAmount()).isEqualTo(0);
    }
}
