package com.example.jpa.source.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MOVIE")
public class Movie extends Item {
	private String director;
	private String actor;

	protected Movie() {

	}

	private Movie(String name, int price, int stockQuantity, String director, String actor) {
		super(name, price, stockQuantity);
		this.director = director;
		this.actor = actor;
	}

	public static Movie of(String name, int price, int stockQuantity, String director, String actor) {
		return new Movie(name, price, stockQuantity, director, actor);
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getDirector() {
		return director;
	}

	public String getActor() {
		return actor;
	}

	@Override
	public String toString() {
		return "Movie{" +
			"director='" + director + '\'' +
			", actor='" + actor + '\'' +
			", id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", stockQuantity=" + stockQuantity +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}
}
