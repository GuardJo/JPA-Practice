package com.practice.jpa.chapter07.entity.single;

import java.util.Objects;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "SINGLE_ITEM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class SingleItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int price;
	private String artist;
	private String director;
	private String actor;
	private String author;
	private String isbn;

	protected SingleItem() {

	}

	public SingleItem(Long id, String name, int price, String artist, String director, String actor, String author, String isbn) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.artist = artist;
		this.director = director;
		this.actor = actor;
		this.author = author;
		this.isbn = isbn;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
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
		SingleItem that = (SingleItem)o;
		return price == that.price && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(artist, that.artist)
			&& Objects.equals(director, that.director) && Objects.equals(actor, that.actor) && Objects.equals(author, that.author) && Objects.equals(
			isbn, that.isbn);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, artist, director, actor, author, isbn);
	}

	@Override
	public String toString() {
		return "SingleItem{" +
			"id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", artist='" + artist + '\'' +
			", director='" + director + '\'' +
			", actor='" + actor + '\'' +
			", author='" + author + '\'' +
			", isbn='" + isbn + '\'' +
			'}';
	}
}
