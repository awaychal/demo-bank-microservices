package bank.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CreditCard {
	
	@Id
	private  String credit_card_no;
	
	@Column
	private BigDecimal balance;
	
	@Column
	private BigDecimal card_limit;

	public String getCredit_card_no() {
		return credit_card_no;
	}

	public void setCredit_card_no(String credit_card_no) {
		this.credit_card_no = credit_card_no;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getCard_limit() {
		return card_limit;
	}

	public void setCard_limit(BigDecimal card_limit) {
		this.card_limit = card_limit;
	}
	
}
