package bank.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bank.demo.entity.CreditCard;
import bank.demo.repository.CreditCardRepository;
import bank.demo.request.CreditCardRequest;
import bank.demo.response.CreditCardResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardService {
	
	@Autowired
	private CreditCardRepository creditCardRepository;
	
	
	public CreditCardResponse createCreditCard(CreditCardRequest creditCardRequest) {
		
		CreditCard creditCard = new CreditCard();
		creditCard.setCredit_card_no(creditCardRequest.getCredit_card_no());
		creditCard.setBalance(creditCardRequest.getBalance());
		creditCard.setCard_limit(creditCardRequest.getCard_limit());
		
		creditCard = creditCardRepository.save(creditCard);
		
		return new CreditCardResponse(creditCard);
	}
	
	public CreditCardResponse getCreditCardByCardNo(String cardNo) {
		CreditCard creditCard = creditCardRepository.findById(cardNo).get();
		
		return new CreditCardResponse(creditCard);
		
	}

    public List<CreditCardResponse> getCreditCards() {
		List<CreditCard> creditCards = creditCardRepository.findAll();

		List<CreditCardResponse> creditCardResponses = creditCards.stream().map(creditCard -> {
			return new CreditCardResponse(creditCard);
		}).collect(Collectors.toList());

		return  creditCardResponses;
    }
}
