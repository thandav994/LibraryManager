package com.main.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="authors")
public class AuthorsEntity {
	@Id
	private Integer author_id;
	private String name;
	public Integer getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(Integer author_id) {
		this.author_id = author_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
