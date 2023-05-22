package bank.demo.service;

import bank.demo.entity.Customer;
import bank.demo.feignclient.CreditCardFeignClient;
import bank.demo.repository.CustomerRepository;
import bank.demo.request.CustomerRequest;
import bank.demo.response.CreditCardResponse;
import bank.demo.response.CustomerResponse;
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
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CreditCardFeignClient creditCardFeignClient;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    private CustomerRequest customerRequest;
    private CreditCardResponse expectedCreditCardResponse;
    private CustomerResponse expectedCustomerResponse;

    @Before
    public void setup() {
        customer = new Customer();
        customer.setId(1);
        customer.setFirst_name("John");
        customer.setLast_name("Will");
        customer.setCredit_card_no("5555555555554444");

        customerRequest = new CustomerRequest();
        customerRequest.setId(1);
        customerRequest.setFirst_name("John");
        customerRequest.setLast_name("Will");
        customerRequest.setCredit_card_no("5555555555554444");

        expectedCreditCardResponse = new CreditCardResponse();
        expectedCreditCardResponse.setCredit_card_no("5555555555554444");
        expectedCreditCardResponse.setCard_limit(BigDecimal.valueOf(1000));
        expectedCreditCardResponse.setBalance(BigDecimal.valueOf(100));

        expectedCustomerResponse = new CustomerResponse();
        expectedCustomerResponse.setId(1);
        expectedCustomerResponse.setFirst_name("John");
        expectedCustomerResponse.setLast_name("Will");
        expectedCustomerResponse.setCreditCardResponse(expectedCreditCardResponse);
    }

    @DisplayName("JUnit test for getCustomerById method")
    @Test
    public void getCustomerById_Should_Return_CustomerResponse() {

        Mockito.when(creditCardFeignClient.getCreditCard("5555555555554444")).thenReturn(expectedCreditCardResponse);

        Mockito.when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getCustomerById(customer.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(expectedCustomerResponse, response);
    }

    @DisplayName("JUnit test for createCustomer method")
    @Test
    public void createCustomer_Should_Return_CustomerResponse() {

        Mockito.when(creditCardFeignClient.getCreditCard("5555555555554444")).thenReturn(expectedCreditCardResponse);

        Mockito.when(customerRepository.save(Mockito.any())).thenReturn(customer);

        CustomerResponse response = customerService.createCustomer(customerRequest);

        Assert.assertNotNull(response);
        Assert.assertEquals(expectedCustomerResponse, response);
    }

    @DisplayName("JUnit test for getCustomers method")
    @Test
    public void getCustomers_Should_Return_CustomerResponsesList() {

        Mockito.when(creditCardFeignClient.getCreditCard("5555555555554444")).thenReturn(expectedCreditCardResponse);

        List<Customer> customerList = singletonList(customer);

        Mockito.when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerResponse> customerResponses = customerService.getCustomers();

        Assert.assertNotNull(customerResponses);
        Assert.assertEquals(1, customerResponses.size());
        Assert.assertEquals(expectedCustomerResponse, customerResponses.get(0));
    }
}
