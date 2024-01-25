package com.practice.jpa.chapter07.entity.join;

import java.util.Objects;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK")
@DiscriminatorValue(value = "B")
@PrimaryKeyJoinColumn(name = "BOOK_ID")
public class Book extends Item {
	private String author;
	private String isbn;

	protected Book() {

	}

	private Book(Long id, String name, int price, String author, String isbn) {
		super(id, name, price);
		this.author = author;
		this.isbn = isbn;
	}

	public static Book of(Long id, String name, int price, String author, String isbn) {
		return new Book(id, name, price, author, isbn);
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		Book book = (Book)o;
		return Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), author, isbn);
	}

	@Override
	public String toString() {
		return "Book{" +
			"author='" + author + '\'' +
			", isbn='" + isbn + '\'' +
			"} " + super.toString();
	}
}
