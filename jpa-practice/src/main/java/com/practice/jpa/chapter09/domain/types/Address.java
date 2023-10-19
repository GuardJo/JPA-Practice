package com.practice.jpa.chapter09.domain.types;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {
	@Embedded
	private Zipcode zipcode;
	private String city;
	private String street;

	protected Address() {

	}

	private Address(Zipcode zipcode, String city, String street) {
		this.zipcode = zipcode;
		this.city = city;
		this.street = street;
	}

	public static Address create(Zipcode zipcode, String city, String street) {
		return new Address(zipcode, city, street);
	}

	public Zipcode getZipcode() {
		return zipcode;
	}

	public String getCity() {
		return city;
	}

	public String getStreet() {
		return street;
	}

	@Override
	public String toString() {
		return "Address{" +
			"zipcode='" + zipcode + '\'' +
			", city='" + city + '\'' +
			", street='" + street + '\'' +
			'}';
	}
}
