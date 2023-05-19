package bank.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.demo.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {

}
