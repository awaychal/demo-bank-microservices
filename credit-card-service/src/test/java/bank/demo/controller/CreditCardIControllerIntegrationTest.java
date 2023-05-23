package bank.demo.controller;

import bank.demo.entity.CreditCard;
import bank.demo.repository.CreditCardRepository;
import bank.demo.request.CreditCardRequest;
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
import org.springframework.http.MediaType;
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
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class})
@AutoConfigureMockMvc
public class CreditCardIControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreditCardRepository creditCardRepository;

    private CreditCard creditCard;
    private CreditCardRequest creditCardRequest;

    @Before
    public void setup() {
        creditCard = new CreditCard();
        creditCard.setCredit_card_no("5555555555554444");
        creditCard.setCard_limit(BigDecimal.valueOf(1000));
        creditCard.setBalance(BigDecimal.valueOf(100));

        creditCardRequest = new CreditCardRequest();
        creditCardRequest.setCredit_card_no("5555555555554444");
        creditCardRequest.setCard_limit(BigDecimal.valueOf(1000));
        creditCardRequest.setBalance(BigDecimal.valueOf(100));
    }

    @Test
    public void getCreditCardByCardNo_Should_Return_CreditCardResponse() throws Exception {

        Mockito.when(creditCardRepository.findById("5555555555554444")).thenReturn(Optional.of(creditCard));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/creditCard/getByCreditCardNo/" + creditCardRequest.getCredit_card_no()).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void createCreditCard_Should_Return_CreditCardResponse() throws Exception {

        Mockito.when(creditCardRepository.save(Mockito.any())).thenReturn(creditCard);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/creditCard/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(creditCardRequest))
                .contentType(APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void getAllCreditCards_Should_Return_CreditCardResponsesList() throws Exception {

        List<CreditCard> creditCards = singletonList(creditCard);

        Mockito.when(creditCardRepository.findAll()).thenReturn(creditCards);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/creditCard")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String expected = "[{\"credit_card_no\":\"5555555555554444\",\"balance\":100,\"card_limit\":1000}]";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

}
