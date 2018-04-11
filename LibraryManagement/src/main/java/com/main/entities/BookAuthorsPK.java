package com.main.entities;

import java.io.Serializable;

public class BookAuthorsPK implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected AuthorsEntity author;
	protected BookEntity book;
	
	public BookAuthorsPK() {}
	
	public BookAuthorsPK(AuthorsEntity author, BookEntity book) {
		this.author = author;
		this.book = book;
	}
}