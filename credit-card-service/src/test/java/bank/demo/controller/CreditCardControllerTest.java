package bank.demo.controller;

import bank.demo.request.CreditCardRequest;
import bank.demo.response.CreditCardResponse;
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
@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardController creditCardController;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getCreditCardByCardNoTest() throws Exception {
        CreditCardResponse creditCard = new CreditCardResponse();
        creditCard.setCredit_card_no("12345678");

        Mockito.when(creditCardController.getCreditCard("12345678")).thenReturn(creditCard);

        mockMvc.perform(get("/api/creditCard/getByCreditCardNo/"+creditCard.getCredit_card_no())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("credit_card_no", is(creditCard.getCredit_card_no())));
    }


    @Test
    public void createCreditCardTest() throws Exception {
        CreditCardRequest creditCard = new CreditCardRequest();
        creditCard.setCredit_card_no("5555555555554444");
        creditCard.setCard_limit(BigDecimal.valueOf(1000));

        CreditCardResponse creditCardResponse = new CreditCardResponse();
        creditCardResponse.setCredit_card_no("5555555555554444");
        creditCardResponse.setCard_limit(BigDecimal.valueOf(1000));

        Mockito.when(creditCardController.createCreditCard(creditCard)).thenReturn(creditCardResponse);
        mockMvc.perform(post("/api/creditCard/create")
                            .contentType(APPLICATION_JSON)
                             .content(this.mapper.writeValueAsString(creditCard)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("credit_card_no", is(creditCard.getCredit_card_no())));
    }

    @Test
    public void getCreditCards() throws Exception {
        CreditCardResponse creditCard = new CreditCardResponse();
        creditCard.setCredit_card_no("12345678");

        List<CreditCardResponse> creditCards = singletonList(creditCard);

        Mockito.when(creditCardController.getCreditCards()).thenReturn(creditCards);

        mockMvc.perform(get("/api/creditCard")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].credit_card_no", is(creditCard.getCredit_card_no())));
    }

}