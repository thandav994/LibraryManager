package com.main.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="book_loans")
public class BookLoansEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer loan_id;
	@ManyToOne
	@JoinColumn(name="isbn")
	private BookEntity book;
	@ManyToOne
	@JoinColumn(name="card_id")
	private BorrowerEntity borrower;
	@Temporal(TemporalType.DATE)
	private Date date_out;
	@Temporal(TemporalType.DATE)
	private Date due_date;
	@Temporal(TemporalType.DATE)
	private Date date_in;
	
	public Integer getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(Integer loan_id) {
		this.loan_id = loan_id;
	}
	public BookEntity getBook() {
		return book;
	}
	public void setBook(BookEntity book) {
		this.book = book;
	}
	public BorrowerEntity getBorrower() {
		return borrower;
	}
	public void setBorrower(BorrowerEntity borrower) {
		this.borrower = borrower;
	}
	public Date getDate_out() {
		return date_out;
	}
	public void setDate_out(Date date_out) {
		this.date_out = date_out;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public Date getDate_in() {
		return date_in;
	}
	public void setDate_in(Date date_in) {
		this.date_in = date_in;
	}
}
