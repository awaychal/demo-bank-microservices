package bank.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.demo.request.CustomerRequest;
import bank.demo.response.CustomerResponse;
import bank.demo.service.CustomerService;

@RestController
@RequestMapping(value = "/api/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@PostMapping(value = "/create")
	public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest){
		return customerService.createCustomer(customerRequest);
	}
	
	@GetMapping(value = "/getById/{id}")
	public CustomerResponse getCustomerById(@PathVariable long id){
		return customerService.getCustomerById(id);
	}
}
