package com.switchfully.eurder.api;

import com.switchfully.eurder.Utility;
import com.switchfully.eurder.api.customer.CreateCustomerDto;
import com.switchfully.eurder.api.customer.CustomerController;
import com.switchfully.eurder.api.customer.CustomerDto;
import com.switchfully.eurder.api.customer.CustomerMapper;
import com.switchfully.eurder.domain.user.Address;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.repository.CustomerRepository;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CustomerControllerTest {
    @LocalServerPort
    private int port;

    private Customer tim;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    CustomerController customerController;

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

    @Test
    void createCustomer_givenACustomerToCreateWithAnInvalidEmailAddress_thenAnErrorIsReturned() {
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("Tom", "Sch", new Address("Teststreet", "14", "2300", "Turnhout"), "tom", "0123456789");

        String message =
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
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .extract()
                        .path("message");

        assertThat(message).isEqualTo("tom is not a valid email address");
    }

    @Test
    void createCustomer_givenACustomerToCreateWithADuplicateEmailAddress_thenAnErrorIsReturned() {
        tim = new Customer("Tim", "Bae", new Address("street", "14", "2300", "Turnhout"), "tim@bae.com", "0123456789");
        customerRepository.addCustomer(tim);
        CreateCustomerDto createCustomerDto = new CreateCustomerDto("Tom", "Sch", new Address("Teststreet", "14", "2300", "Turnhout"), "tim@bae.com", "0123456789");

        String message =
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
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .extract()
                        .path("message");

        assertThat(message).isEqualTo(createCustomerDto.emailAddress() + " is already in use, please provide an other email address");

    }

    @Test
    void getAllCustomers_givenAdminAccessAndACustomerTim_thenTimIsInTheReturnedList() {
        tim = new Customer("Tim", "Bae", new Address("street", "14", "2300", "Turnhout"), "tim@bae.com", "0123456789");
        customerRepository.addCustomer(tim);

        CustomerDto[] customerDtos =
                RestAssured
                        .given()
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("default@admin.com", "123"))
                        .when()
                        .port(port)
                        .get("/customers")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(CustomerDto[].class);


        assertThat(customerDtos).contains(customerMapper.customerToCustomerDto(tim));
    }

    @Test
    void getCustomer_givenAdminAccessAndACustomerTim_thenTimIsReturned() {
        tim = new Customer("Tim", "Bae", new Address("street", "14", "2300", "Turnhout"), "tim@bae.com", "0123456789");
        customerRepository.addCustomer(tim);

        CustomerDto customerDto =
                RestAssured
                        .given()
                        .contentType(JSON)
                        .header("Authorization", Utility.generateBase64Authorization("default@admin.com", "123"))
                        .when()
                        .port(port)
                        .get("/customers/" + tim.getId())
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(CustomerDto.class);


        assertThat(customerDto).isEqualTo(customerMapper.customerToCustomerDto(tim));
    }

    @Test
    void customerControllerTestWithoutRestAssured() {
        CustomerDto customerDto =  customerController.createCustomer(new CreateCustomerDto("integration", "test", new Address("1", "2", "3", "4"), "integration@test.com", "1234"));

        assertThat(customerDto.firstName()).isEqualTo("integration");
    }
}
