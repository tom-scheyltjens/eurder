package com.switchfully.eurder.api;

import com.switchfully.eurder.domain.Address;
import com.switchfully.eurder.repository.CustomerRepository;
import com.switchfully.eurder.service.CustomerService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CustomerControllerTest {
    @Value("${server.port}")
    private int port;
    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @Autowired
    CustomerControllerTest(CustomerRepository customerRepository, CustomerService customerService) {
        this.customerRepository = customerRepository;
        this.customerService = customerService;
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
}
