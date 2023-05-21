package bank.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.demo.request.CreditCardRequest;
import bank.demo.response.CreditCardResponse;
import bank.demo.service.CreditCardService;

@RestController
@RequestMapping(value = "/api/creditCard")
public class CreditCardController {
	
	@Autowired
	private CreditCardService creditCardService;

	@PostMapping(value = "/create")
	public CreditCardResponse createCreditCard(@RequestBody CreditCardRequest creditCardRequest){
		return creditCardService.createCreditCard(creditCardRequest);
	}
	
	@GetMapping(value = "/getByCreditCardNo/{cardNo}")
	public CreditCardResponse getCreditCard(@PathVariable String cardNo) {
		return creditCardService.getCreditCardByCardNo(cardNo);
	}
}
