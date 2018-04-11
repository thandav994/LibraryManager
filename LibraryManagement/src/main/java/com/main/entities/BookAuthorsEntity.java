package com.main.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.main.entities.BookAuthorsPK;

@Entity
@Table(name="book_authors")
@IdClass(BookAuthorsPK.class)
public class BookAuthorsEntity {
	@Id
	@ManyToOne
	@JoinColumn(name="author_id")
	private AuthorsEntity author;
	@Id
	@ManyToOne
	@JoinColumn(name="isbn")
	private BookEntity book;
	
	public AuthorsEntity getAuthor() {
		return author;
	}
	public void setAuthor(AuthorsEntity author) {
		this.author = author;
	}
	public BookEntity getBook() {
		return book;
	}
	public void setBook(BookEntity book) {
		this.book = book;
	}
	
	
}
