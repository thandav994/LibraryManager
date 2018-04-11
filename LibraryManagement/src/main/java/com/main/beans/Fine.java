package com.main.beans;

public class Fine {
	private Integer loan_id;
	private Double fine_amt;
	private boolean paid;
	private boolean checkedIn;
	private Borrower borrower;
	
	
	public Integer getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(Integer loan_id) {
		this.loan_id = loan_id;
	}
	public Double getFine_amt() {
		return fine_amt;
	}
	public void setFine_amt(Double fine_amt) {
		this.fine_amt = fine_amt;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public boolean isCheckedIn() {
		return checkedIn;
	}
	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}
	public Borrower getBorrower() {
		return borrower;
	}
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}
}
