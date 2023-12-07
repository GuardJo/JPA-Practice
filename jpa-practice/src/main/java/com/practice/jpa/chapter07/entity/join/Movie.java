package com.practice.jpa.chapter07.entity.join;

import java.util.Objects;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MOVIE")
@DiscriminatorValue(value = "M")
@PrimaryKeyJoinColumn(name = "MOVIE_ID")
public class Movie extends Item {
	private String director;
	private String actor;

	protected Movie() {

	}

	private Movie(Long id, String name, int price, String director, String actor) {
		super(id, name, price);
		this.director = director;
		this.actor = actor;
	}

	public static Movie of(Long id, String name, int price, String director, String actor) {
		return new Movie(id, name, price, director, actor);
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		Movie movie = (Movie)o;
		return Objects.equals(director, movie.director) && Objects.equals(actor, movie.actor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), director, actor);
	}

	@Override
	public String toString() {
		return "Movie{" +
			"director='" + director + '\'' +
			", actor='" + actor + '\'' +
			"} " + super.toString();
	}
}
