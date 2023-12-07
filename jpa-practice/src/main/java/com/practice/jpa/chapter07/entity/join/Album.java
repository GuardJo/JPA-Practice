package com.practice.jpa.chapter07.entity.join;

import java.util.Objects;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "ALBUM")
@DiscriminatorValue(value = "A")
@PrimaryKeyJoinColumn(name = "ALBUM_ID")
public class Album extends Item {
	private String artist;

	protected Album() {

	}

	private Album(Long id, String name, int price, String artist) {
		super(id, name, price);
		this.artist = artist;
	}

	public static Album of(Long id, String name, int price, String artist) {
		return new Album(id, name, price, artist);
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;
		Album album = (Album)o;
		return Objects.equals(artist, album.artist);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), artist);
	}

	@Override
	public String toString() {
		return "Album{" +
			"artist='" + artist + '\'' +
			"} " + super.toString();
	}
}
