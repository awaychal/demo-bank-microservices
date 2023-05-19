package bank.demo.response;

import bank.demo.entity.Customer;

public class CustomerResponse {
	
	private long id;
	
	private String first_name;
	
	private String last_name;
	
	private CreditCardResponse creditCardResponse;
	
	public CustomerResponse(Customer customer) {
		this.id = customer.getId();
		this.first_name = customer.getFirst_name();
		this.last_name = customer.getLast_name();
		//this.address_id = student.getAddress_id();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public CreditCardResponse getCreditCardResponse() {
		return creditCardResponse;
	}

	public void setCreditCardResponse(CreditCardResponse creditCardResponse) {
		this.creditCardResponse = creditCardResponse;
	}


	

	
	
}
