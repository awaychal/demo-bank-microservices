package bank.demo.controller;

import bank.demo.entity.Customer;
import bank.demo.feignclient.CreditCardFeignClient;
import bank.demo.repository.CustomerRepository;
import bank.demo.request.CustomerRequest;
import bank.demo.response.CreditCardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CreditCardFeignClient creditCardFeignClient;

    private Customer customer;
    private CustomerRequest customerRequest;
    private CreditCardResponse expectedCreditCardResponse;

    @Before
    public void setup() {
        customer = new Customer();
        customer.setId(1);
        customer.setFirst_name("John");
        customer.setLast_name("Will");
        customer.setCredit_card_no("5555555555554444");

        customerRequest = new CustomerRequest();
        customerRequest.setId(1);
        customerRequest.setFirst_name("John");
        customerRequest.setLast_name("Will");
        customerRequest.setCredit_card_no("5555555555554444");

        expectedCreditCardResponse = new CreditCardResponse();
        expectedCreditCardResponse.setCredit_card_no("5555555555554444");
        expectedCreditCardResponse.setCard_limit(BigDecimal.valueOf(1000));
        expectedCreditCardResponse.setBalance(BigDecimal.valueOf(100));
    }

    @Test
    public void getCustomerById_Should_Return_CustomerResponse() throws Exception {

        Mockito.when(creditCardFeignClient.getCreditCard("5555555555554444")).thenReturn(expectedCreditCardResponse);

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/customer/getById/" + customer.getId())
                .contentType(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":1,\"first_name\":\"John\",\"last_name\":\"Will\",\"creditCardResponse\":{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createCustomer_Should_Return_CustomerResponse() throws Exception {

        Mockito.when(creditCardFeignClient.getCreditCard("5555555555554444")).thenReturn(expectedCreditCardResponse);

        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/customer/create")
                .contentType(APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(customerRequest));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"id\":1,\"first_name\":\"John\",\"last_name\":\"Will\",\"creditCardResponse\":{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getAllCustomers_Should_Return_CustomerResponsesList() throws Exception {

        Mockito.when(creditCardFeignClient.getCreditCard("5555555555554444")).thenReturn(expectedCreditCardResponse);

        List<Customer> customerList = singletonList(customer);

        Mockito.when(customerRepository.findAll()).thenReturn(customerList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/customer")
                .contentType(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"id\":1,\"first_name\":\"John\",\"last_name\":\"Will\",\"creditCardResponse\":{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}}]";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}
