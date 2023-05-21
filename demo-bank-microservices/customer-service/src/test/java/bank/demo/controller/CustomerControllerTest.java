package bank.demo.controller;


import bank.demo.request.CustomerRequest;
import bank.demo.response.CreditCardResponse;
import bank.demo.response.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    public CustomerControllerTest() {
    }

    @Test
    public void getCustomer() throws Exception {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(1);
        customerResponse.setFirst_name("abc");

        Mockito.when(customerController.getCustomerById(1)).thenReturn(customerResponse);

        mockMvc.perform(get("/api/customer/getById/"+customerResponse.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("first_name", is(customerResponse.getFirst_name())));
    }


    @Test
    public void createCustomer() throws Exception {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFirst_name("abc");
        customerRequest.setCredit_card_no("5555555555554444");

        CreditCardResponse creditCardResponse = new CreditCardResponse();
        creditCardResponse.setCredit_card_no("5555555555554444");
        creditCardResponse.setCard_limit(BigDecimal.valueOf(1000));
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setFirst_name("abc");
        customerResponse.setCreditCardResponse(creditCardResponse);

        Mockito.when(customerController.createCustomer(customerRequest)).thenReturn(customerResponse);
        mockMvc.perform(post("/api/customer/create")
                            .contentType(APPLICATION_JSON)
                             .content(this.mapper.writeValueAsString(customerRequest)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("first_name", is(customerResponse.getFirst_name())));
    }

    @Test
    public void getCustomers() throws Exception {
        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setId(1);
        customerResponse.setFirst_name("abc");

        List<CustomerResponse> customerResponses = singletonList(customerResponse);

        Mockito.when(customerController.getCustomers()).thenReturn(customerResponses);

        mockMvc.perform(get("/api/customer")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].first_name", is(customerResponse.getFirst_name())));
    }

}