package com.main.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="fines")
public class FinesEntity {
	@Id
	private Integer loan_id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@MapsId
	@JoinColumn(name="loan_id")
	private BookLoansEntity loan;
	private Double fine_amt;
	private boolean paid;
	
	public Integer getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(Integer loan_id) {
		this.loan_id = loan_id;
	}
	public Double getFine_amt() {
		return fine_amt;
	}
	public void setFine_amt(double d) {
		this.fine_amt = d;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public BookLoansEntity getLoan() {
		return loan;
	}
	public void setLoan(BookLoansEntity loan) {
		this.loan = loan;
	}
	public void setFine_amt(Double fine_amt) {
		this.fine_amt = fine_amt;
	}
}
