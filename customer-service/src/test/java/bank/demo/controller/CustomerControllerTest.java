package bank.demo.controller;


import bank.demo.request.CustomerRequest;
import bank.demo.response.CreditCardResponse;
import bank.demo.response.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerController customerController;

    @Autowired
    private ObjectMapper mapper;

    private CustomerRequest customerRequest;
    private CustomerResponse customerResponse;

    private CreditCardResponse creditCardResponse;

    @Before
    public void setup() {

        customerRequest = new CustomerRequest();
        customerRequest.setId(1);
        customerRequest.setFirst_name("John");
        customerRequest.setLast_name("Will");
        customerRequest.setCredit_card_no("5555555555554444");

        creditCardResponse = new CreditCardResponse();
        creditCardResponse.setCredit_card_no("5555555555554444");
        creditCardResponse.setCard_limit(BigDecimal.valueOf(1000));
        creditCardResponse.setBalance(BigDecimal.valueOf(100));

        customerResponse = new CustomerResponse();
        customerResponse.setId(1);
        customerResponse.setFirst_name("John");
        customerResponse.setLast_name("Will");
        customerResponse.setCreditCardResponse(creditCardResponse);

    }

    @DisplayName("JUnit test for getCustomerById method")
    @Test
    public void getCustomerById_Should_Return_CustomerResponse() throws Exception {

        Mockito.when(customerController.getCustomerById(1)).thenReturn(customerResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/customer/getById/" + customerResponse.getId())
                .contentType(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":1,\"first_name\":\"John\",\"last_name\":\"Will\",\"creditCardResponse\":{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @DisplayName("JUnit test for createCustomer method")
    @Test
    public void createCustomer_Should_Return_CustomerResponse() throws Exception {

        Mockito.when(customerController.createCustomer(customerRequest)).thenReturn(customerResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/customer/create")
                .contentType(APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(customerRequest));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":1,\"first_name\":\"John\",\"last_name\":\"Will\",\"creditCardResponse\":{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }


    @DisplayName("JUnit test for getAllCustomers method")
    @Test
    public void getAllCustomers_Should_Return_CustomerResponsesList() throws Exception {

        List<CustomerResponse> customerResponses = singletonList(customerResponse);

        Mockito.when(customerController.getAllCustomers()).thenReturn(customerResponses);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/customer")
                .contentType(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"first_name\":\"John\",\"last_name\":\"Will\",\"creditCardResponse\":{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

}