package bank.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bank.demo.entity.Customer;
import bank.demo.feignclient.CreditCardFeignClient;
import bank.demo.repository.CustomerRepository;
import bank.demo.request.CustomerRequest;
import bank.demo.response.CreditCardResponse;
import bank.demo.response.CustomerResponse;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CreditCardFeignClient creditCardFeignClient;
	
	public CustomerResponse createCustomer(CustomerRequest customerRequest) {
		
		Customer customer = new Customer();
		customer.setFirst_name(customerRequest.getFirst_name());
		customer.setLast_name(customerRequest.getLast_name());
		customer.setCredit_card_no(customerRequest.getCredit_card_no());
		
		customer = customerRepository.save(customer);
		
		CreditCardResponse creditCardResponse = creditCardFeignClient.getCreditCard(customerRequest.getCredit_card_no());
	
		CustomerResponse customerResponse = new CustomerResponse(customer);
		customerResponse.setCreditCardResponse(creditCardResponse);
		
		return customerResponse;
	}
	
	public CustomerResponse getCustomerById(Long id) {
		Customer customer = customerRepository.findById(id).get();
		
		CreditCardResponse creditCardResponse = creditCardFeignClient.getCreditCard(customer.getCredit_card_no());
		
		CustomerResponse customerResponse = new CustomerResponse(customer);
		customerResponse.setCreditCardResponse(creditCardResponse);
		
		return customerResponse;
		
	}
}
