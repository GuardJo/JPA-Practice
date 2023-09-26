package com.example.jpa.source.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BOOK")
public class Book extends Item {
	private String author;
	private String isbn;

	protected Book() {

	}

	private Book(String name, int price, int stockQuantity, String author, String isbn) {
		super(name, price, stockQuantity);
		this.author = author;
		this.isbn = isbn;
	}

	public static Book of(String name, int price, int stockQuantity, String author, String isbn) {
		return new Book(name, price, stockQuantity, author, isbn);
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public String getIsbn() {
		return isbn;
	}

	@Override
	public String toString() {
		return "Book{" +
			"author='" + author + '\'' +
			", isbn='" + isbn + '\'' +
			", id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", stockQuantity=" + stockQuantity +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}
}
