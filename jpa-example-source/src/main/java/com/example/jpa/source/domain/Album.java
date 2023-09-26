package com.example.jpa.source.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ALBUM")
public class Album extends Item {
	@Column(length = 50)
	private String artist;
	private String etc;

	protected Album() {
	}

	private Album(String name, int price, int stockQuantity, String artist, String etc) {
		super(name, price, stockQuantity);
		this.artist = artist;
		this.etc = etc;
	}

	public static Album of(String name, int price, int stockQuantity, String artist, String etc) {
		return new Album(name, price, stockQuantity, artist, etc);
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

	public String getArtist() {
		return artist;
	}

	public String getEtc() {
		return etc;
	}

	@Override
	public String toString() {
		return "Album{" +
			"artist='" + artist + '\'' +
			", etc='" + etc + '\'' +
			", id=" + id +
			", name='" + name + '\'' +
			", price=" + price +
			", stockQuantity=" + stockQuantity +
			", createdDate=" + createdDate +
			", modifiedDate=" + modifiedDate +
			'}';
	}
}
