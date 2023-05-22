package bank.demo.controller;

import bank.demo.request.CreditCardRequest;
import bank.demo.response.CreditCardResponse;
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
import org.springframework.http.MediaType;
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
@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardController creditCardController;

    @Autowired
    private ObjectMapper mapper;

    private CreditCardRequest creditCardRequest;
    private CreditCardResponse creditCardResponse;

    @Before
    public void setup() {

        creditCardRequest = new CreditCardRequest();
        creditCardRequest.setCredit_card_no("5555555555554444");
        creditCardRequest.setCard_limit(BigDecimal.valueOf(1000));
        creditCardRequest.setBalance(BigDecimal.valueOf(100));

        creditCardResponse = new CreditCardResponse();
        creditCardResponse.setCredit_card_no("5555555555554444");
        creditCardResponse.setCard_limit(BigDecimal.valueOf(1000));
        creditCardResponse.setBalance(BigDecimal.valueOf(100));
    }

    @DisplayName("JUnit test for getCreditCard method")
    @Test
    public void getCreditCard_Should_Return_CreditCardResponse() throws Exception {

        Mockito.when(creditCardController.getCreditCard("5555555555554444")).thenReturn(creditCardResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/creditCard/getByCreditCardNo/" + creditCardResponse.getCredit_card_no()).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}";

        System.out.println(expected);

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @DisplayName("JUnit test for createCreditCard method")
    @Test
    public void createCreditCard_Should_Return_CreditCardResponse() throws Exception {

        Mockito.when(creditCardController.createCreditCard(creditCardRequest)).thenReturn(creditCardResponse);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/creditCard/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(creditCardRequest))
                .contentType(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}";

        System.out.println(expected);

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @DisplayName("JUnit test for getAllCreditCards method")
    @Test
    public void getAllCreditCards_Should_Return_CreditCardResponsesList() throws Exception {

        List<CreditCardResponse> creditCards = singletonList(creditCardResponse);

        Mockito.when(creditCardController.getAllCreditCards()).thenReturn(creditCards);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/creditCard")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}]";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

}