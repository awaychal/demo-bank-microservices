package bank.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
