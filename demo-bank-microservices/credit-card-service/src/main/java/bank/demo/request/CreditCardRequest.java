package bank.demo.request;

import java.math.BigDecimal;
import java.util.Objects;

public class CreditCardRequest {
	
	private String credit_card_no;
	
	private BigDecimal balance;
	
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CreditCardRequest that = (CreditCardRequest) o;
		return Objects.equals(credit_card_no, that.credit_card_no) && Objects.equals(balance, that.balance) && Objects.equals(card_limit, that.card_limit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(credit_card_no, balance, card_limit);
	}

}
