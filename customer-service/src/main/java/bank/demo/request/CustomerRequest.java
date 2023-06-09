package bank.demo.request;

import java.util.Objects;

public class CustomerRequest {
	
	private long id;
	
	private String first_name;
	
	private String last_name;
	
	private String credit_card_no;

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

	public String getCredit_card_no() {
		return credit_card_no;
	}

	public void setCredit_card_no(String credit_card_no) {
		this.credit_card_no = credit_card_no;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CustomerRequest that = (CustomerRequest) o;
		return id == that.id && Objects.equals(first_name, that.first_name) && Objects.equals(last_name, that.last_name) && Objects.equals(credit_card_no, that.credit_card_no);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, first_name, last_name, credit_card_no);
	}
}
