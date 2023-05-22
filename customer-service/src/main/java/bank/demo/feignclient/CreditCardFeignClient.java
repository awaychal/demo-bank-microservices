package bank.demo.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import bank.demo.response.CreditCardResponse;

@FeignClient(url="http://credit-card-service:8082", name="credit-card-service", path = "/api/creditCard")
public interface CreditCardFeignClient {

	@GetMapping(value = "/getByCreditCardNo/{cardNo}")
	public CreditCardResponse getCreditCard(@PathVariable String cardNo);
	
}
