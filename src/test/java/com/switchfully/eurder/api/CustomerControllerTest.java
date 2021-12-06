package com.switchfully.eurder.api;

import com.switchfully.eurder.api.customer.CreateCustomerDto;
import com.switchfully.eurder.api.customer.CustomerDto;
import com.switchfully.eurder.domain.Address;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerTest {
    @Value("${server.port}")
    private int port;

    @Test
    void createCustomer_givenACustomerToCreate_thenTheNewlyCreatedCustomerIsSavedAndReturned() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("Tom", "Sch", new Address("Teststreet", "14", "2300", "Turnhout"), "tom@sch.com", "0123456789");

        CustomerDto customerDto =
                RestAssured
                        .given()
                        .body(createCustomerDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/customers")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(CustomerDto.class);


        assertThat(customerDto.firstName()).isEqualTo(createCustomerDto.firstName());
        assertThat(customerDto.lastName()).isEqualTo(createCustomerDto.lastName());
        assertThat(customerDto.address()).isEqualTo(createCustomerDto.address());
        assertThat(customerDto.emailAddress()).isEqualTo(createCustomerDto.emailAddress());
        assertThat(customerDto.phoneNumber()).isEqualTo(createCustomerDto.phoneNumber());
    }

    @Test
    void createCustomer_givenACustomerToCreateWithEmptyFirstName_thenAnErrorMessageIsReturnedWithHttpStatusBadRequest() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("", "Sch", new Address("Teststreet", "14", "2300", "Turnhout"), "tom@sch.com", "0123456789");


        RestAssured
                .given()
                .body(createCustomerDto)
                .accept(JSON)
                .contentType(JSON)
                .when()
                .port(port)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
