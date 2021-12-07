package com.switchfully.eurder.api;

import com.switchfully.eurder.Utility;
import com.switchfully.eurder.api.customer.CreateCustomerDto;
import com.switchfully.eurder.api.customer.CustomerDto;
import com.switchfully.eurder.api.customer.CustomerMapper;
import com.switchfully.eurder.domain.user.Address;
import com.switchfully.eurder.domain.user.Customer;
import com.switchfully.eurder.repository.CustomerRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerControllerTest {
    @Value("${server.port}")
    private int port;
    private Customer tim;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerControllerTest(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @BeforeAll
    public void setUp(){
        tim = new Customer("Tim", "Bae", new Address("street", "14", "2300", "Turnhout"), "tim@bae.com", "0123456789");
        customerRepository.addCustomer(tim);
    }

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
    void getAllCustomers_givenAdminAccessAndACustomerTim_thenTimIsInTheReturnedList() {

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
    void getCustomer_givenAdminAccessAndACustomerTim_thenTimIsInTheReturned() {

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

}
