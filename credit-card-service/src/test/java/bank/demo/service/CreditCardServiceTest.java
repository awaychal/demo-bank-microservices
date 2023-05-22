package bank.demo.service;

import bank.demo.entity.CreditCard;
import bank.demo.repository.CreditCardRepository;
import bank.demo.request.CreditCardRequest;
import bank.demo.response.CreditCardResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;

@RunWith(SpringRunner.class)
public class CreditCardServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardService creditCardService;

    private CreditCard creditCard;
    private CreditCardRequest creditCardRequest;
    private CreditCardResponse expectedResponse;

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

        expectedResponse = new CreditCardResponse();
        expectedResponse.setCredit_card_no("5555555555554444");
        expectedResponse.setCard_limit(BigDecimal.valueOf(1000));
        expectedResponse.setBalance(BigDecimal.valueOf(100));
    }

    @DisplayName("JUnit test for getCreditCardByCardNo method")
    @Test
    public void getCreditCardByCardNo_Should_Return_CreditCardResponse() {

        Mockito.when(creditCardRepository.findById("5555555555554444")).thenReturn(Optional.of(creditCard));

        CreditCardResponse response = creditCardService.getCreditCardByCardNo(creditCard.getCredit_card_no());

        Assert.assertNotNull(response);
        Assert.assertEquals(expectedResponse, response);
    }

    @DisplayName("JUnit test for createCreditCard method")
    @Test
    public void createCreditCard_Should_Return_CreditCardResponse() {

        Mockito.when(creditCardRepository.save(Mockito.any())).thenReturn(creditCard);

        CreditCardResponse creditCardResponse = creditCardService.createCreditCard(creditCardRequest);

        Assert.assertNotNull(creditCardResponse);
        Assert.assertEquals(expectedResponse, creditCardResponse);
    }

    @DisplayName("JUnit test for getCreditCards method")
    @Test
    public void getCreditCards_Should_Return_CreditCardResponsesList() throws Exception {

        List<CreditCard> creditCards = singletonList(creditCard);

        Mockito.when(creditCardRepository.findAll()).thenReturn(creditCards);

        List<CreditCardResponse> creditCardResponses = creditCardService.getCreditCards();

        Assert.assertNotNull(creditCardResponses);
        Assert.assertEquals(1, creditCardResponses.size());
        Assert.assertEquals(expectedResponse, creditCardResponses.get(0));
    }

}
